package com.shamsid.sociallogin.utils;

import java.util.Arrays;
import java.util.List;



public class Helper {

  public static final String FACEBOOK_USER_PROFILE = "https://graph.facebook.com/%1$s/picture?type=large";
  public static final List<String> FACEBOOK_PERMISSION_LIST = Arrays.asList ("email","public_profile");
  public static final String SELECT_SOCIAL_PLATFORM_MESSAGE = "select social platform";
  public static final String GOOGLE_PLUS_SIGN_IN_ERROR = "google plus sign in error";
  public static int GPLUS_RESULT = 1001;
  public static final String CLIENT_ID = "CLIENT_ID";

}
