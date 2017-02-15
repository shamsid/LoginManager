package com.shamsid.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shamsid.sociallogin.models.Profile;
import com.shamsid.sociallogin.utils.Helper;

public class GooglePlusActivity extends FragmentActivity implements
    GoogleApiClient.OnConnectionFailedListener {


  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);

    GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.
        Builder (GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail ().build ();

    GoogleApiClient googleApiClient =
        new GoogleApiClient.Builder (this).enableAutoManage (this, this)
            .addApi (Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build ();

    Intent intent = Auth.GoogleSignInApi.getSignInIntent (googleApiClient);
    startActivityForResult (intent, Helper.GPLUS_RESULT);

  }

  @Override
  public void onConnectionFailed (@NonNull ConnectionResult connectionResult) {
    finish ();
  }

  @Override
  protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    super.onActivityResult (requestCode, resultCode, data);

    if (requestCode == Helper.GPLUS_RESULT) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent (data);
      try {
        handleSignInResult (result);
      }catch (GooglePlusSignInError error){
        error.printStackTrace ();
      }
    }
  }

  public void handleSignInResult(GoogleSignInResult result) throws GooglePlusSignInError{
    if(result.isSuccess ()){

      GoogleSignInAccount googleSignInAccount =result.getSignInAccount ();
      Profile profileInformation = new Profile ();
      profileInformation.setFullName (googleSignInAccount.getDisplayName ());
      profileInformation.setEmailAddress (googleSignInAccount.getEmail ());
      profileInformation.setProfileId (googleSignInAccount.getId ());
      profileInformation.setUserProfileUrl (googleSignInAccount.getPhotoUrl ()!= null ?
          googleSignInAccount.getPhotoUrl ().toString ():"");
      profileInformation.setAccessToken (googleSignInAccount.getIdToken ());
      com.shamsid.sociallogin.LoginManager.getInstance (this).onLoginSuccess (profileInformation);

    }else{
      com.shamsid.sociallogin.LoginManager.getInstance (this).onLoginError (new GooglePlusSignInError ());
    }
    finish ();
  }
}
