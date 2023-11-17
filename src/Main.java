import models.Email;
import models.User;
import patterns.Singleton;
import security.AuthenticationManager;
import security.Register;
import security.SecurityContext;
import sockets.EmailSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
  static Socket  socket;
  static DataInputStream dataInputStream;
  static ObjectInputStream objectInputStream;
  static ObjectOutputStream objectOutputStream;
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    while (SecurityContext.user == null){
      System.out.println("Select an option: ");
      System.out.println("1. Register \n 2. Login");
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          Register register = new Register();
          User user = register.register();
          SecurityContext.user = user;
          break;
        case 2:
          AuthenticationManager login = new AuthenticationManager();
          String email = scanner.nextLine();
          String password = scanner.nextLine();
          login.authenticate(email,password);
          break;
        default:
          System.out.println("Invalid option");
          break;
      }
    }


      try {
        socket = new Socket("localhost", 8080);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject(SecurityContext.user);
        Thread listenerThread = new Thread(Main::listenEmails);
        listenerThread.start();

        while (true) {
          System.out.println("Select an option: ");
          System.out.println("1. Send email \n 2. Read emails \n 3. Logout");

          int option2 = scanner.nextInt();
          scanner.nextLine();

          switch (option2) {
            case 1:
              System.out.println("Enter the email of the recipient: ");
              String emailRecipient = scanner.nextLine();
              System.out.println("Enter the message ");

              String message = scanner.nextLine();
              Email email = new Email(SecurityContext.user, message, emailRecipient);
              sendEmail(email);
              break;
            case 2:
              List<Email> emails = Singleton.getInstance().emailService.getEmails(SecurityContext.user);
              if(emails == null){
                System.out.println("You don't have emails");
                break;
              }

              emails.forEach(e -> {
                System.out.println("From: " + e.getSender().getEmail());
                System.out.println("Message: " + e.getMessage());
              });
              break;
            case 3:
              System.out.println("Loggin out...");
              SecurityContext.user = null;
              System.exit(0);
            default:
              System.out.println("Invalid option");
              break;
          }
        }
      } catch (IOException e) {
        System.out.println("Error during the server connection: " + e.getMessage());
        e.printStackTrace();
      }


  }
  public static void listenEmails() {
    try {
      while (socket.isConnected()) {

        Email message = (Email) objectInputStream.readObject();
        System.out.println("New message received");
        System.out.println("From: " + message.getSender().getEmail());
        System.out.println("Message received: " + message.getMessage());
    }
    }catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
  }
  public static void sendEmail(Email email){
    try {
      objectOutputStream.writeObject(email);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}