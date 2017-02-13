package com.shamsid.sociallogin;

import com.shamsid.sociallogin.utils.Helper;



public class SocialPlatformNotFound extends Exception {

  public SocialPlatformNotFound(){
    super(Helper.SELECT_SOCIAL_PLATFORM_MESSAGE);
  }
}
