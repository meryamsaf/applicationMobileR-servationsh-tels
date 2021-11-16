package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
private EditText userName,userMail,userPass,userAdress,userPhone;
private TextView alreadyOpt;
private Button register;
private CheckBox asclient,asHotelier;
private FirebaseAuth fAuth;
private FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName=findViewById(R.id.nomRegister);
        userMail=findViewById(R.id.registerMail);
        userPass=findViewById(R.id.passwordlRegister);
        userAdress=findViewById(R.id.adressUser);
        register=findViewById(R.id.register);
        userPhone=findViewById(R.id.phoneNumber);
        asclient=findViewById(R.id.clientcheckBox);
        asHotelier=findViewById(R.id.asHotelier);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        alreadyOpt=findViewById(R.id.alreadyhave);

        alreadyOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this,Login_activity.class);
                startActivity(intent);
                finish();
            }
        });
        asclient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    asHotelier.setChecked(false);
                }
            }
        });
        asHotelier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(asHotelier.isChecked()){
                    asclient.setChecked(false);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=userMail.getText().toString().trim();
                String password=userPass.getText().toString().trim();
                String fullName=userName.getText().toString().trim();
                String phone=userPhone.getText().toString().trim();
                String adresse=userAdress.getText().toString().trim();

                if(fullName.isEmpty()){
                    userName.setError("Full name is required");
                    userName.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    userPhone.setError("phone number is required");
                    userPhone.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    userMail.setError("email is required");
                    userMail.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    userPass.setError("password is required");
                    userPass.requestFocus();
                    return;
                }

                if(password.length()<6){
                    userPass.setError("min password length should be 6 characters!");
                    userPass.requestFocus();
                    return;
                }
                if(adresse.isEmpty()){
                    userAdress.setError("adress is required!");
                    userAdress.requestFocus();
                    return;
                }
                if(!(asHotelier.isChecked() ||asclient.isChecked())){
                    Toast.makeText(RegisterActivity.this, "select the Account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(RegisterActivity.this, "Data Validated", Toast.LENGTH_SHORT).show();
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user=fAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        DocumentReference df=fStore.collection("Users").document(user.getUid());
                        Map<String,Object>userInfo =new HashMap<>();
                        userInfo.put("FullName",fullName);
                        userInfo.put("EmailUser",email);
                        userInfo.put("PhoneNumber",phone);
                        userInfo.put("Adress",adresse);
                        if(asclient.isChecked()){
                            userInfo.put("isClient","1");
                            df.set(userInfo);
                            Intent intent1=new Intent(RegisterActivity.this,ProfileClientActivity.class);
                            intent1.putExtra("FullName",fullName);
                            intent1.putExtra("E-mail",email);
                            intent1.putExtra("iddUser",user.getUid());
                            startActivity(intent1);
                            finish();
                        }
                        if(asHotelier.isChecked()){
                            userInfo.put("isHotelier","1");
                            df.set(userInfo);
                            Intent intent =new Intent(RegisterActivity.this,HotelierProfile.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed to create an account!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}