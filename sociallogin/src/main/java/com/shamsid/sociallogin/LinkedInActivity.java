package com.shamsid.sociallogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.shamsid.sociallogin.models.Profile;
import com.shamsid.sociallogin.utils.Helper;
import org.json.JSONException;
import org.json.JSONObject;

public class LinkedInActivity extends Activity {

  JSONObject jsonObject;

  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);

    LISessionManager.getInstance(getApplicationContext()).init(LinkedInActivity.this,
        buildScope(), new AuthListener () {
      @Override
      public void onAuthSuccess() {

        profileInformation();
      }

      @Override
      public void onAuthError(LIAuthError error) {
        LoginManager.getInstance (LinkedInActivity.this).onLoginError
            (new LinkedInAuthException (error.toString ()));
      }
    }, true);
  }

  private static Scope buildScope() {
    return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE,Scope.R_EMAILADDRESS);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
  }

  private void profileInformation(){
    final Profile profileInformation = new Profile ();


    APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());

    apiHelper.getRequest(LinkedInActivity.this, Helper.LINKEDIN_URl, new ApiListener () {
      @Override
      public void onApiSuccess(ApiResponse apiResponse) {
        try {

          jsonObject = apiResponse.getResponseDataAsJson ();
          profileInformation.setEmailAddress (jsonObject.getString ("emailAddress"));
          profileInformation.setFullName (jsonObject.getString ("firstName")+" " + jsonObject.getString ("lastName"));
          profileInformation.setUserProfileUrl (jsonObject.getJSONObject ("pictureUrls").getJSONArray ("values").get (0).toString ());
          profileInformation.setProfileId (jsonObject.getString ("id"));
          profileInformation.setAccessToken ("");
          LoginManager.getInstance (LinkedInActivity.this).onLoginSuccess (profileInformation);
          finish ();
        }catch (JSONException e){
          LoginManager.getInstance (LinkedInActivity.this).onLoginError
              (e);
        }
      }

      @Override
      public void onApiError(LIApiError liApiError) {
        LoginManager.getInstance (LinkedInActivity.this).onLoginError
            (new LinkedInAuthException (liApiError.toString ()));
        finish ();
      }
    });
  }


}

