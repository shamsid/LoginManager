package com.shamsid.sociallogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.shamsid.sociallogin.models.User;
import com.shamsid.sociallogin.utils.Helper;
import org.json.JSONException;
import org.json.JSONObject;

import static com.shamsid.sociallogin.utils.Helper.FACEBOOK_PERMISSION_LIST;



public class FacebookActivity extends Activity implements FacebookCallback<LoginResult> ,
    GraphRequest.GraphJSONObjectCallback{

  private CallbackManager mCallbackManager;

  @Override protected void onCreate (@Nullable Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);

    mCallbackManager = CallbackManager.Factory.create ();
    LoginManager.getInstance ().registerCallback (mCallbackManager,this);
    LoginManager.getInstance ().logOut ();

    LoginManager.getInstance ().logInWithReadPermissions (this,FACEBOOK_PERMISSION_LIST);

  }

  @Override
  public void onSuccess (LoginResult loginResult) {
    GraphRequest graphRequest = GraphRequest.newMeRequest (loginResult.getAccessToken (),this);
    Bundle parameters = new Bundle ();
    parameters.putString ("fields", "id,name,email");
    graphRequest.setParameters (parameters);
    graphRequest.executeAsync ();

  }

  @Override
  public void onCancel () {
    finish ();

  }


  @Override public void onError (FacebookException error) {
    error.printStackTrace ();
    finish ();
  }

  @Override
  public void onCompleted (JSONObject object, GraphResponse response){
    try{
      User userInformation = new User ();
      userInformation.setId (object.getString ("id"));
      userInformation.setAccessToken (AccessToken.getCurrentAccessToken ().getToken ());
      userInformation.setUserProfileUrl (String.format (Helper.FACEBOOK_USER_PROFILE,userInformation.getId ()));
      userInformation.setEmailAddress (object.has ("email")?object.getString ("email"):"");
      userInformation.setFullName (object.has ("name")?object.getString ("name"):"");
      com.shamsid.sociallogin.LoginManager.getInstance (this).setUserInfo (userInformation);

    }catch(JSONException e){

      e.printStackTrace ();
    }
    finally {
      LoginManager.getInstance ().logOut ();
      finish ();
    }

  }

  @Override protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    super.onActivityResult (requestCode, resultCode, data);
    mCallbackManager.onActivityResult (requestCode,resultCode,data);
  }

}
