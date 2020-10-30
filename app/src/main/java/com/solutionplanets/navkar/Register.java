package com.solutionplanets.navkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");

    TextView btn;
    Button register;
    private EditText uName, email, pw, confPW;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        uName = findViewById(R.id.userNameET);
        email = findViewById(R.id.emailET);
        pw = findViewById(R.id.pwET);
        confPW = findViewById(R.id.confpwET);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Register.this);

        register = findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CheckCredentials();
            }
        });
        

        
        btn = findViewById(R.id.signInTV);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void CheckCredentials() {
        String username = uName.getText().toString().trim();
        String emailId = email.getText().toString().trim();
        String password = pw.getText().toString().trim();
        String confirmPw = confPW.getText().toString().trim();

        if(username.isEmpty()){
            errorMsg(uName, "Please enter username");
        } else if(emailId.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
            errorMsg(email, "Please enter valid email address");
        }else if(password.isEmpty() || password.length()<6){
            errorMsg(pw, "Password must be at least 7 character");
        }else if(confirmPw.isEmpty() || !confirmPw.equals(password)){
            errorMsg(confPW, "Password does not match");
        }else {
            mLoadingBar.setTitle("Registration in process");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    private void errorMsg(EditText field, String msg) {
       field.setError(msg);
       field.requestFocus();
    }
}