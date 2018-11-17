package com.example.artur.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artur.app.UserProfile;
import com.example.artur.app.MainActivity;
import com.example.artur.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText UserPassword;
    private EditText UserEmail;
    private EditText UserNote;
    private Button regButton;
    private TextView userLogin;
    String email, name, password, note;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){

                    //Upload data to the database//

                    String user_email = UserEmail.getText().toString().trim();
                    String user_password = UserPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                //send user data to database
                                sendUserData();
                                firebaseAuth.signOut();

                                Toast.makeText(RegistrationActivity.this, "Registration Successful and data were uploaded", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });


    }
    private void setupUIViews() {
        UserName = (EditText) findViewById(R.id.etUserName);
        UserEmail = (EditText) findViewById(R.id.etUserEmail);
        UserPassword = (EditText) findViewById(R.id.etUserPassword);
        UserNote = (EditText)findViewById(R.id.etUserNote);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);

    }

    private Boolean validate(){
        Boolean result = false;

        name = UserName.getText().toString();
        password =  UserPassword.getText().toString();
        email = UserEmail.getText().toString();
        note = UserNote.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || note.isEmpty()){
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return  result;


    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name, note);
        myRef.setValue(userProfile);
    }
}
