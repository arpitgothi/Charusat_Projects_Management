package com.example.prakash.charusatprojects;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.SignInOptions;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    EditText email3, password3;
    ProgressBar progressBar;
    SignInButton signin;
    GoogleApiClient googleApiClient;
    int flag=0;
    DatabaseHelper db;
    private static final int REQ_CODE=9001;
    LinearLayout Prof_Section;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        email3 = (EditText) findViewById( R.id.editTextEmail );
        password3 = (EditText) findViewById( R.id.editTextPassword );
        progressBar = (ProgressBar) findViewById( R.id.progressbar );
        signin=(SignInButton)findViewById(R.id.bn_login) ;
        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        Prof_Section=findViewById(R.id.rel);
        Prof_Section.setVisibility(View.GONE);

        Button signup = (Button) findViewById( R.id.buttonsignup );
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( LoginActivity.this, RegistrationActivity.class );
                startActivity( i );
            }

        } );
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        Button login = (Button) findViewById( R.id.buttonLogin );
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
               if (flag==0) {
                    Intent iii = new Intent( LoginActivity.this, Dashboard.class );
                     startActivity( iii );
                }

            }
        } );

    }


    private void userLogin() {
        String email = email3.getText().toString().trim();
        String password = password3.getText().toString().trim();

        if (email.isEmpty()) {
            flag=1;
            email3.setError( "Email is required" );
            email3.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            flag=1;
            email3.setError( "Please enter a valid email" );
            email3.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            flag=1;
            password3.setError( "Password is required" );
            password3.requestFocus();
            return;
        }

        if (password.length() < 6) {
            flag=1;
            password3.setError( "Minimum lenght of password should be 6" );
            password3.requestFocus();
            return;
        }

        Boolean checkemail= db.checkemail( email );
        Boolean checkpass= db.checkpass( password );
        if (checkemail==true || checkpass==true){
            //Boolean insert = db.insert(  );
           // if(insert==true){
                Toast.makeText( getApplicationContext(),"Login Successful",Toast.LENGTH_LONG ).show();
           // }

        }
        else if (checkemail==true || checkpass==false)
        {
            Toast.makeText( this, "Enter Correct password !!", Toast.LENGTH_SHORT ).show();
           // flag=1;
        }

        else if (checkemail==false || checkpass==true) {
            Toast.makeText( this, "Enter Correct id !!", Toast.LENGTH_SHORT ).show();
           // flag=1;
        }

        else
        {
            Toast.makeText( this, "Email doesn't Exist please register first", Toast.LENGTH_SHORT ).show();
           // flag=1;
        }

        progressBar.setVisibility( View.VISIBLE );

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signin()
    {
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    private void signout()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }
    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account=result.getSignInAccount();
           // String password=account.getDisplayName();
            String email=account.getEmail();
            email3.setText(email);
            updateUI(true);
        }
        else
            updateUI(false);
    }

    private void updateUI(boolean isLogin)
    {
        if(isLogin)
        {
            Prof_Section.setVisibility(View.VISIBLE);
            signin.setVisibility(View.GONE);
        }
        else
        {
            Prof_Section.setVisibility(View.GONE);
            signin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
