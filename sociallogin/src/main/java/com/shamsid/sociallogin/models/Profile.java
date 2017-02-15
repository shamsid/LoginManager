package com.shamsid.sociallogin.models;

import java.io.Serializable;

public class Profile implements Serializable ,Cloneable{

  public Profile (Profile profile){
    this.fullName = profile.getFullName ();
    this.emailAddress = profile.getEmailAddress ();
    this.accessToken = profile.getAccessToken ();
    this.userProfileUrl = profile.getUserProfileUrl ();
  }
  public Profile (){
  }
  private String id;
  private String userProfileUrl;
  private String accessToken;
  private String emailAddress;
  private String fullName;

  public String getId () {
    return id;
  }

  public void setId (String id) {
    this.id = id;
  }

  public String getEmailAddress () {
    return emailAddress;
  }

  public void setEmailAddress (String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getFullName () {
    return fullName;
  }

  public void setFullName (String fullName) {
    this.fullName = fullName;
  }



  public String getUserProfileUrl () {
    return userProfileUrl;
  }

  public void setUserProfileUrl (String userProfileUrl) {
    this.userProfileUrl = userProfileUrl;
  }

  public String getAccessToken () {
    return accessToken;
  }

  public void setAccessToken (String accessToken) {
    this.accessToken = accessToken;
  }

  public Object clone()throws CloneNotSupportedException{
    return super.clone();
  }
}
