package com.shamsid.sociallogin;

import com.shamsid.sociallogin.utils.Helper;

/**
 * Created by shamsheR on 13/02/17.
 */

public class GooglePlusSignInError extends Exception {

  public GooglePlusSignInError(){
    super(Helper.GOOGLE_PLUS_SIGN_IN_ERROR);
  }
}
