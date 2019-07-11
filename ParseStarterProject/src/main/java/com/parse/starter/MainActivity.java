/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static com.google.android.gms.analytics.internal.zzy.i;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    boolean signUpModeActive = true;
    TextView changeSignupModeTextView;
    EditText passwordEditText;


    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER  &&  event.getAction() == KeyEvent.ACTION_DOWN){

            signUp(v);

        }

        return false;
    }


    public  void onClick(View view){

        if(view.getId()==R.id.changeSignupModeTextView){

            Button signupButton = (Button)findViewById(R.id.signupButton);

            if(signUpModeActive){

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Sign up");

            }else{


                signUpModeActive = true;
                signupButton.setText("Sign Up");
                changeSignupModeTextView.setText("Or, Login");

            }

        }else if (view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView){

            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }

    }


    public void signUp(View view){

        EditText usernameEditText = (EditText) findViewById( R.id.usernameEditText);


        if(usernameEditText.getText().toString().matches("")  ||  passwordEditText.getText().toString().matches("")){

            Toast.makeText(MainActivity.this,"A username and password are required",Toast.LENGTH_LONG).show();

        }else {

            if (signUpModeActive){

                ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        Log.i("Signup", "Successful");
                        showUserList();

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });

        }else{

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user !=null){

                            Log.i("Sign up ","Login successful");
                            showUserList();

                        }else{

                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        changeSignupModeTextView = (TextView)findViewById(R.id.changeSignupModeTextView);

        changeSignupModeTextView.setOnClickListener(this);


        RelativeLayout backgroundRelativeLayout = (RelativeLayout)findViewById((R.id.backgroundRelativeLayout));

        ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);

        backgroundRelativeLayout.setOnClickListener(this);

        logoImageView.setOnClickListener(this);

        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        passwordEditText.setOnKeyListener((View.OnKeyListener) this);


        if(ParseUser.getCurrentUser() != null){

            showUserList();

        }



        ParseAnalytics.trackAppOpenedInBackground(getIntent());






        /**
         * Logging out the user
         */
      /*

        ParseUser.logOut();


        */

        /**
         * Checking if the user is logged in already
         */
/*
        if(ParseUser.getCurrentUser() != null){

            Log.i("CurrentUser","User Logged in " + ParseUser.getCurrentUser().getUsername());

        }else{

            Log.i("Current user","User not Logged in");

        }
     */


        /**
         * Login in code
         * Logging in  a new user into the app.
         */

        /*
        ParseUser.logInInBackground("Shakti", "asjfjd", new LogInCallback() {  // Wrong password used, it gives invalid username/password exception
            @Override
            public void done(ParseUser user, ParseException e) {

                if(user!=null){
                    Log.i("Login","Successful");
                }else{
                    Log.i("Login","Failed" + e.toString());
                }

            }
        });
        */


        /**
         * Sign up code
         * Signing up a new user
         */

        /*
        ParseUser user = new ParseUser();

        user.setUsername("Shakti");
        user.setPassword("myPass");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){
                    Log.i("SignUp", "Successful");
                }else{
                    Log.i("SignUp","Failed");
                }

            }
        });
    */

        /**
         * The below comments show how to find or get the data from the parse
         * according to the search criteria like greater than something or equal to something
         */
        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.whereGreaterThan("score",200);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects != null){

                    for(ParseObject object : objects){
                        object.put("score",object.getInt("score")+50);
                        object.saveInBackground();
                    }

                }
            }
        });



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");


        //query.whereEqualTo("username","Tommy");
        //query.setLimit(1);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){
                    Log.i("findInBackground","Retrieved "+objects.size()+" objects");

                    if(objects.size()>0){
                        for(ParseObject object : objects){
                            Log.i("FindInBackground",Integer.toString(object.getInt("score")));
                        }
                    }

                }

            }
        });
  */

        /**
         * The commented items are a demonstration of how to create a class on parse server;
         * How to put data into it.
         * How to save it on the parse server.
         * How to retrive the data .
         * And how to update the data after retrieving it from the parse server.
         */

/*
    ParseObject score = new ParseObject("Score");
    score.put("username","rob");
    score.put("score",86);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if(e==null){
          Log.i("SavedInBackground","Successful");
        }else{
          Log.i("SaveInBackground","Failed . Error :" + e.toString());
        }


      }
    });

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("z4hTTp9tpN", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e==null && object!=null){
          object.put("score",200);
          object.saveInBackground();

          Log.i("Object Value",object.getString("username"));
          Log.i("SCore",Integer.toString((object.getInt("score"))));
        }
      }
    });



   ParseObject tweet = new ParseObject("Tweet");
   tweet.put("tweet","This is testing tweet!");
   tweet.put("name","Donald Trump.");
   tweet.saveInBackground();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.getInBackground("ZoZXVd4IMR", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
                    Log.i("Tweet ", "Successful");

                } else {
                    Log.i("Tweet", "Failed");
                }
                object.put("tweet", "President of America");
                object.saveInBackground();
            }
        });
         */

    }


}