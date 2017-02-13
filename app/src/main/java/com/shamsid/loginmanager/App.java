package com.shamsid.loginmanager;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import com.shamsid.sociallogin.LoginManager;

/**
 * Created by shamsheR on 13/02/17.
 */

public class App extends Application {

  @Override public void onCreate () {
    super.onCreate ();
    LoginManager.init (this);
  }
}
