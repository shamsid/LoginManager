package com.shamsid.sociallogin;

import com.shamsid.sociallogin.utils.Helper;

/**
 * Created by shamsheR on 12/02/17.
 */

public class SocialPlatformNotFound extends Exception {

  public SocialPlatformNotFound(){
    super(Helper.SELECT_SOCIAL_PLATFORM_MESSAGE);
  }
}
