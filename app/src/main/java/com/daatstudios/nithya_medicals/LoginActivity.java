package com.daatstudios.nithya_medicals;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login =  findViewById(R.id.login_btn);



        login.setOnClickListener(v->{

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build());


            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTheme(R.style.Theme_aUTHui)
                    .setLogo(R.drawable.logo_nithiya)
                    .build();
            signInLauncher.launch(signInIntent);
        });



    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            Map<String,String> dbmap = new HashMap<>();
            dbmap.put("UID",user.getUid());


            long time  =  user.getMetadata().getLastSignInTimestamp();

            if (time>0) {
                firestore.collection("Users").add(dbmap);
                Intent intent = new Intent(LoginActivity.this,UserDetailsActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }


        } else {
            Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show();
        }
    }
}