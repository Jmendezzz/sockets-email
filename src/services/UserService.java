package services;

import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
  private List<User> users;
  public UserService(){
    this.users = new ArrayList<>();
  }

  public void registerUser(User user) {
    users.add(user);
  }
  public Optional<User> getUserByEmail(String email) {
  return users.stream()
          .filter(u->u.getEmail().equals(email))
          .findFirst();
  }
  public List<User> getUsers() {
    return users;
  }

}
