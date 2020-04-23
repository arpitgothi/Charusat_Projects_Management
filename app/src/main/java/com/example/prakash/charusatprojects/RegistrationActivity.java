package com.example.prakash.charusatprojects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText fname1,lname1,email1,password1,phone1,charusatid1;
    ProgressBar progressBar;
    int flag=0;
    Button b1;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        db=new DatabaseHelper( this );
         b1 = (Button) findViewById( R.id.btsignup );
         fname1 = (EditText) findViewById( R.id.fname );
         lname1 = (EditText) findViewById( R.id.lname );
         charusatid1 = (EditText) findViewById( R.id.charusatid );
         email1 = (EditText) findViewById( R.id.email );
         password1 = (EditText) findViewById( R.id.password );
         phone1 = (EditText) findViewById( R.id.phonno );
         progressBar=(ProgressBar)findViewById(R.id.progressbar);


       Button regbt=(Button)findViewById( R.id.btsignup );
       regbt.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              userRegistration();
              if(flag==0) {
                  Intent ii = new Intent( RegistrationActivity.this, LoginActivity.class );
                  startActivity( ii );
              }
           }
       } );

    }


        private void userRegistration() {
            String fname2 = fname1.getText().toString().trim();
            String lname2 = lname1.getText().toString().trim();
            String charusatid2 = charusatid1.getText().toString().trim();
            String email2 = email1.getText().toString().trim();
            String password2 = password1.getText().toString().trim();
            String phone2 = phone1.getText().toString().trim();


            if (fname2.isEmpty()) {
               flag=1;
                fname1.setError( "First Name is required" );
                fname1.requestFocus();
                return;
            }

            if (lname2.isEmpty()) {
                flag=1;
                lname1.setError( "Last Name is required" );
                lname1.requestFocus();
                return;
            }

            if (charusatid2.isEmpty()) {
                flag=1;
                charusatid1.setError( "Charusat Id is required" );
                charusatid1.requestFocus();
                return;
            }

            if (email2.isEmpty()) {
                flag=1;
                email1.setError( "Email is required" );
                email1.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher( email2 ).matches()) {
                flag=1;
                email1.setError( "Please enter a valid email" );
                email1.requestFocus();
                return;
            }

            if (password2.isEmpty()) {
                flag=1;
                password1.setError( "Password is required" );
                password1.requestFocus();
                return;
            }

            if (password2.length() < 6) {
                flag=1;
                password1.setError( "Minimum lenght of password should be 6" );
                password1.requestFocus();
                return;
            }

            if (phone2.isEmpty()) {
                flag=1;
                phone1.setError( "Contact Number is required" );
                phone1.requestFocus();
                return;
            }

            if (phone2.length() < 10) {
                flag=1;
                phone1.setError( "Minimum lenght of Contact Number should be 10" );
                phone1.requestFocus();
                return;
            }

            Boolean checkid= db.checkid( charusatid2 );
            if (checkid==true){
                Boolean insert = db.insert( fname2,lname2,charusatid2,email2,password2,phone2 );
                if(insert==true){
                    Toast.makeText( getApplicationContext(),"Registration Successful Now you can Login",Toast.LENGTH_LONG ).show();
                }
            }
            else
            {
                Toast.makeText( this, "Charusat Id is already exist ! Login with this id", Toast.LENGTH_SHORT ).show();
            }

            progressBar.setVisibility( View.VISIBLE );

        }


}
