package security;

import models.User;
import patterns.Singleton;
import services.UserService;

import java.util.Scanner;

public class Register {
  private UserService userService;
  public Register() {
    this.userService = Singleton.getInstance().userService;
  }
  public User register() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter your email: ");
    String email = scanner.nextLine();
    System.out.println("Enter your password: ");
    String password = scanner.nextLine();
    User user = new User(email, password);

    userService.registerUser(user);
    return user;
  }
}
