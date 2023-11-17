package models;

import java.io.Serializable;

public class Email implements Serializable {
  private User sender;
  private  String message;
  private String recipientEmail;

  public Email(User sender, String message, String recipientEmail) {
    this.sender = sender;
    this.message = message;
    this.recipientEmail = recipientEmail;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRecipientEmail() {
    return recipientEmail;
  }

  public void setRecipientEmail(String recipientEmail) {
    this.recipientEmail = recipientEmail;
  }
}
