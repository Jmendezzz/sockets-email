package sockets;


import models.Email;
import models.User;
import patterns.Singleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmailSocket {
  private static List<EmailSocket> usersActive = new ArrayList<>();
  private User user;
  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream ObjectInputStream;
  public EmailSocket(Socket socket, User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
          this.socket = socket;
          this.user = user;
          try {
            this.objectOutputStream = objectOutputStream;
            this.ObjectInputStream = objectInputStream;
            usersActive.add(this);
          } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
          }
        }

  public void listenEmails(){
    while (socket.isConnected()){
      try {
        Email email = (Email) ObjectInputStream.readObject();
        usersActive.stream()
                .filter(u-> u.user.getEmail().equals(email.getRecipientEmail()))
                .findFirst()
                .ifPresent(u->{
                  try {
                    u.getObjectOutputStream().writeObject(email);
                  } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                  }
                });
        Singleton.getInstance().emailService.addEmail(email);
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public ObjectOutputStream getObjectOutputStream() {
    return objectOutputStream;
  }

  public java.io.ObjectInputStream getObjectInputStream() {
    return ObjectInputStream;
  }
}
