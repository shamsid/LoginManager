package com.shamsid.loginmanager;

import android.app.Application;
import com.shamsid.sociallogin.LoginManager;



public class App extends Application {

  @Override public void onCreate () {
    super.onCreate ();
    LoginManager.init (this);
  }
}
