package services;

import models.Email;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailService {
  private Map<String, List<Email>> emails;
  public EmailService() {
    this.emails = new HashMap<>();
  }

  public void addEmail(Email email) {
    List<Email> userEmails = emails.get(email.getSender());
    if(userEmails == null) {
      userEmails = new ArrayList<>();
    }
    userEmails.add(email);
    emails.put(email.getRecipientEmail(), userEmails);
  }
  public List<Email> getEmails(User user) {
    return emails.get(user.getEmail());
  }
}
