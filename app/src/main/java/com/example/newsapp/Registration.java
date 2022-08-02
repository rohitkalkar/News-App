package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name , email,age,password ;
    Button register,goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        name=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        age=findViewById(R.id.age);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        goback=findViewById(R.id.button2);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = name.getText().toString().trim();
                String emailid = email.getText().toString().trim();
                String Age = age.getText().toString().trim();
                String pssw = password.getText().toString().trim();
                // validations.
                if (fullname.isEmpty()){
                    name.setError("Full Name is required");
                    name.requestFocus();
                    return;
                }
                if (emailid.isEmpty()){
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
                    email.setError("please provide valid email");
                    email.requestFocus();
                    return;
                }
                if (Age.isEmpty()){
                    age.setError("Age is requred");
                    age.requestFocus();
                    return;
                }
                if (pssw.isEmpty()){
                    password.setError("Passwor is requred");
                    password.requestFocus();
                    return;
                }
                if (pssw.length()<6){
                    password.setError("Minimum password length should be 6 chareters");
                    password.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailid,pssw )//provide string values to method
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user=new User(fullname,emailid,Age,pssw);
                                    //Here we get instance of database
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            //here we get user id to store other values accordingly
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(Registration.this, "user has been registered succsesfully", Toast.LENGTH_LONG).show();
                                                        
                                                    }else {
                                                        Toast.makeText(Registration.this, "failed to register try again", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else {
                                    Toast.makeText(Registration.this, "failed to register try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });


    }
}