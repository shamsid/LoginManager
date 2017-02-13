package com.shamsid.sociallogin;

import com.shamsid.sociallogin.utils.Helper;


public class GooglePlusSignInError extends Exception {

  public GooglePlusSignInError(){
    super(Helper.GOOGLE_PLUS_SIGN_IN_ERROR);
  }
}
