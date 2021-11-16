package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login_activity extends AppCompatActivity {
    EditText loginEmail,loginPass;
    Button signInButton;
    TextView signUpOption,forgotOption;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        loginEmail=findViewById(R.id.email_login);
        loginPass=findViewById(R.id.password_log);
        signInButton=findViewById(R.id.buttonLogin);
       signUpOption=findViewById(R.id.textViewSignUp);
        forgotOption=findViewById(R.id.forgotpassword);



        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        signUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_activity.this,RegisterActivity.class));
                finish();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=loginEmail.getText().toString().trim();
                String pass=loginPass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    loginPass.setError("PassWord  is required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                   @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login_activity.this,"Loggedin successfully",Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login_activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });


    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess:" + documentSnapshot.getData());

                if(documentSnapshot.getString("isHotelier")!=null){
                    startActivity(new Intent(getApplicationContext(),HotelierProfile.class));
                    finish();
                }
                if(documentSnapshot.getString("isClient")!=null){
                    startActivity(new Intent(getApplicationContext(),ProfileClientActivity.class));
                    finish();
                }
            }
        });
    }


}