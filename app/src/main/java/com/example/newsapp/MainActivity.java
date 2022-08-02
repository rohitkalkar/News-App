package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email ,password;
    Button btn ;
    TextView tv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
       email=findViewById(R.id.email);
        password=findViewById(R.id.Password);
        btn=findViewById(R.id.button);
        tv=findViewById(R.id.textView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Registration.class);
                startActivity(intent);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (Email.isEmpty()){
                    email.setError("enter the valid email id ");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("email id is not valid try again");
                    email.requestFocus();
                    return;

                }
                if (Password.isEmpty()){
                    password.setError("enter the password please");
                    password.requestFocus();
                    return;

                }
                if (Password.length()<6){
                    password.setError("password should be atleast 6 charecters");
                    password.requestFocus();
                    return;
                }
                 mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(MainActivity.this, "sign up succsesfully", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(MainActivity.this,NewsActivity.class));

                         }else {
                             Toast.makeText(MainActivity.this, "FAILED TO LOG IN", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });



            }
        });
    }
}