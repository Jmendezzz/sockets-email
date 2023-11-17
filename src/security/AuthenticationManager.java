package security;

import models.User;
import patterns.Singleton;
import services.UserService;

import java.util.Optional;

public class AuthenticationManager {
  private UserService userService;
  public AuthenticationManager() {
    this.userService = Singleton.getInstance().userService;
  }
  public boolean authenticate(String email, String password) {
    Optional<User> user = userService.getUserByEmail(email);
    if(user.isPresent()) {
      if(user.get().getPassword().equals(password)) {
        SecurityContext.user = user.get();
        return true;
      }
    }
    return  false;

  }
}
