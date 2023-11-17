package patterns;

import models.Email;
import services.EmailService;
import services.UserService;

public class Singleton {

  private static Singleton instance;
  public EmailService emailService;
  public UserService userService;

  private Singleton() {
    emailService = new EmailService();
    userService = new UserService();
  }

  public static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }

}
