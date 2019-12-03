package com.test.demo.pojo;

import java.io.Serializable;

public class User implements Serializable {

  private long userid;
  private String username;
  private String password;

    public User() {
    }

    public User(long userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
