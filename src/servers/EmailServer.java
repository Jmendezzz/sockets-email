package servers;

import models.User;
import patterns.Singleton;
import security.SecurityContext;
import sockets.EmailSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EmailServer {
  public static void main(String[] args) {

    try {
      ServerSocket serverSocket = new ServerSocket(8080, 10);
      while (!serverSocket.isClosed()) {
        System.out.println("Waiting for new client...");
        // Accept incoming connections
        Socket socket = serverSocket.accept();
        System.out.println("New client connected");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        User user = (User) objectInputStream.readObject();
        System.out.println(user.getEmail());
        Singleton.getInstance().userService.registerUser(user);
        EmailSocket emailSocket = new EmailSocket(socket, user,objectOutputStream,objectInputStream);
        Thread newThread = new Thread(emailSocket::listenEmails);
        newThread.start();
      }
    } catch (IOException e) {
      System.out.println("Server error: " + e.getMessage());
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
