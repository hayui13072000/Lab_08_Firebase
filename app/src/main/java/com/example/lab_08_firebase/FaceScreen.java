package com.example.lab_08_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FaceScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgSmile, imgBored, imgAngry;
    private Button btnFinish;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseFirestore db;
    private User user;
    private String userId;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_screen);

        imgAngry=findViewById(R.id.imgAngry);
        imgBored=findViewById(R.id.imgBored);
        imgSmile=findViewById(R.id.imgSmile);
        btnFinish=findViewById(R.id.btnFinish);

        db = FirebaseFirestore.getInstance();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId = null;
            } else {
                userId= extras.getString("USERID");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("USERID");
        }
        documentReference = db.collection("Users").document(userId);
        getData();

        imgAngry.setOnClickListener(this);
        imgBored.setOnClickListener(this);
        imgSmile.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    private void getData(){
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgAngry:{
                int happy= user.getAngry()+1;
                user.setAngry(happy);
                Toast.makeText(FaceScreen.this, "Angry :(", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imgBored:{
                int unhappy=user.getBored()+1;
                user.setBored(unhappy);
                Toast.makeText(FaceScreen.this, "Bored :|", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imgSmile:{
                int nor=user.getSmile()+1;
                user.setSmile(nor);
                Toast.makeText(FaceScreen.this, "Smile :)", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnFinish:{
                documentReference.update(
                        "angry", user.getAngry(),
                        "bored", user.getBored(),
                        "smile", user.getSmile()
                );
                Toast.makeText(FaceScreen.this, "Update successful!!!", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
}