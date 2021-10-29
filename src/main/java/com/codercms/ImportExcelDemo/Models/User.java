package com.codercms.ImportExcelDemo.Models;

import java.util.UUID;

/**
 * Model Class to Map entities with database
 */
public class User {

  public String firstname;

  public String email;

  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String contact;


  public String username;
  public User() {
  }





  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }



  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
}