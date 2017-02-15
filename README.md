###Login Manager

[![Release](https://jitpack.io/v/shamsid/LoginManager.svg)](https://jitpack.io/#shamsid/LoginManager)

This android library will reduce the boiler plate code while using social logins in Android Application.

#### Setup 
To use this library your minSdkVersion should be 16 or above.

- In your project level build.gradle file add the following code

```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

- In your app level build.gradle file add this line

```java
dependencies {
	        compile 'com.github.shamsid:LoginManager:v1'
	}
```

###Usage 

####Step 1:
You must setup the AndroidManifest.xml for facebook and google login to use this library

#####Facebook Login
```xml
<meta-data android:name="com.facebook.sdk.ApplicationId"
				android:value="@string/facebook_app_id"/>
<activity android:name="com.facebook.FacebookActivity"
				android:configChanges=
						"keyboard|keyboardHidden|screenLayout|screenSize|orientation"
				android:label="@string/app_name" />


		<activity
				android:name="com.facebook.CustomTabActivity"
				android:exported="true">
			<intent-filter>
				<action android:name="android.intent.action.VIEW" />
				<category android:name="android.intent.category.DEFAULT" />
				<category android:name="android.intent.category.BROWSABLE" />
				<data android:scheme="@string/fb_login_protocol_scheme" />
			</intent-filter>
		</activity>
```
#####Google Login

- Put your `google-services.json` under `app` directory which can created by using this link [`google-serices.json`](https://developers.google.com/mobile/add?platform=android&cntapi=signin&cntapp=Default%20Demo%20App&cntpkg=com.google.samples.quickstart.signin&cnturl=https:%2F%2Fdevelopers.google.com%2Fidentity%2Fsign-in%2Fandroid%2Fstart%3Fconfigured%3Dtrue&cntlbl=Continue%20with%20Try%20Sign-In)

- Add `classpath 'com.google.gms:google-services:3.0.0'` in your project level build.gradle.

- Add `apply plugin: 'com.google.gms.google-services'` in your app level build.gradle

####Step 2:

```java 
public class App extends Application {

  @Override public void onCreate () {
    super.onCreate ();
    LoginManager.init (this);
  }
}
```
####Step 3:

```java
public class LoginManagerActivity extends Activity {

  String TAG = LoginManagerActivity.class.getName ();

  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_login_manager);
    findViewById (R.id.tv_facebook).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginFacebook();
      }
    });
    findViewById (R.id.tv_google_plus).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginGooglePlus();
      }
    });

    findViewById (R.id.tv_twitter).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginTwitter ();
      }
    });

    findViewById (R.id.tv_linkedIn).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginLinkedIn ();
      }
    });


  }

  private void loginFacebook(){

    try {
      LoginManager.getInstance (this)
          .choose (Platforms.FACEBOOK)
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);

          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }

  }

  private void loginGooglePlus(){
    try {
      LoginManager.getInstance (this)
          .choose (Platforms.GOOGLE_PLUS)
          .setClientId (getString (R.string.google_app_id))
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

  private void loginTwitter(){

    try {
      LoginManager.getInstance (this)
          .choose (Platforms.TWITTER)
          .setClientId (getString (R.string.twitter_public))
          .setClientSecretId (getString (R.string.twitter_secret))
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

  private void loginLinkedIn(){

    try{
      LoginManager.getInstance (this)
          .choose (Platforms.LINKEDIN)
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

}
  
```

##License

```
MIT License

Copyright (c) 2017 Shamsher Ahmed

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
