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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainScreenRegiter extends AppCompatActivity {
    private EditText txtName, txtEmail, txtPassword, txtRePass;
    private Button btnRegiter;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String userId;
    private TextView tvSig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_regiter);

        auth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        txtName=findViewById(R.id.txtName);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        txtRePass=findViewById(R.id.txtRePass);
        btnRegiter=findViewById(R.id.btnRegiter);
        tvSig=findViewById(R.id.tvSig);

        tvSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainScreenRegiter.this,MainScreenSignIn.class);
                startActivity(intent);
            }
        });

        btnRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtName.getText().toString().trim();
                String email=txtEmail.getText().toString().trim();
                String pass=txtPassword.getText().toString().trim();
                String repass=txtRePass.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password >6!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(repass)) {
                    Toast.makeText(getApplicationContext(), "repass!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainScreenRegiter.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainScreenRegiter.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainScreenRegiter.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    userId=auth.getCurrentUser().getUid();
                                    User user= new User(name,email, pass,0,0,0);
                                    db.collection("Users").document(userId).set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent= new Intent(MainScreenRegiter.this,MainScreen.class);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainScreenRegiter.this, "ris fail", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }

}