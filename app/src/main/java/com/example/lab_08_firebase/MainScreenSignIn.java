package com.example.lab_08_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreenSignIn extends AppCompatActivity {
    private EditText txtEmail, txtPass;
    private FirebaseAuth auth;
    private Button btnSi;
    private String userId;
    private TextView tvRis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_sign_in);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            auth.signOut();
        }

        auth=FirebaseAuth.getInstance();
        txtEmail=findViewById(R.id.txtSiEmail);
        txtPass=findViewById(R.id.txtSiPass);
        btnSi=findViewById(R.id.button3);
        tvRis=findViewById(R.id.tvRis);

        tvRis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenSignIn.this, MainScreenRegiter.class);
                startActivity(intent);
                finish();
            }
        });

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=txtEmail.getText().toString().trim();
                String pass=txtPass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainScreenSignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainScreenSignIn.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                } else {
                                    userId = auth.getCurrentUser().getUid();
                                    Intent intent = new Intent(MainScreenSignIn.this, FaceScreen.class);
                                    intent.putExtra("USERID", userId);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }
}