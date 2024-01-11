package com.daatstudios.nithya_medicals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailsActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        btn = findViewById(R.id.button4);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();




        btn.setOnClickListener(v->{

        });

    }
}