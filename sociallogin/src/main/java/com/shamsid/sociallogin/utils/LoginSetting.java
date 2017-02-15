package com.shamsid.sociallogin.utils;


/**
 * Created by shamsheR on 14/02/17.
 */

public class LoginSetting {


  private Platforms mSocialPlatform;
  private String clientId;

  public String getClientId () {
    return clientId;
  }

  public Platforms getSocialPlatform () {
    return mSocialPlatform;
  }

  public void setSocialPlatform (Platforms socialPlatform) {
    mSocialPlatform = socialPlatform;
  }

  public void setClientId (String clientId) {
    this.clientId = clientId;

  }
}
