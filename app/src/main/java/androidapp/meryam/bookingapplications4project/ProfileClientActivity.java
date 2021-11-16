package androidapp.meryam.bookingapplications4project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileClientActivity extends AppCompatActivity {
private TextView name,mail;
private Button chercherHot,ajouterReservation,recommandation,notification,sedeconnecter,mesReservations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_client);

        name=findViewById(R.id.nameClient);
        mail=findViewById(R.id.mailClient);
        chercherHot=findViewById(R.id.chercherHotel);
        ajouterReservation=findViewById(R.id.ajouterReservation);
        recommandation=findViewById(R.id.recommandations);
        notification=findViewById(R.id.notification);
        sedeconnecter=findViewById(R.id.seDeconnecter);
        mesReservations=findViewById(R.id.mesReservation);

        Bundle extras=getIntent().getExtras();
        String fullname=extras.getString("FullName");
        String email=extras.getString("E-mail");
        String idClient=extras.getString("iddUser");

        name.setText(fullname);
        mail.setText(email);

        chercherHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileClientActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ajouterReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileClientActivity.this, AddReservation.class);
                intent.putExtra("idClient",idClient);
                startActivity(intent);
            }
        });
        sedeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileClientActivity.this, Login_activity.class);
                startActivity(intent);
            }
        });
       mesReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileClientActivity.this, ShowReservation.class);
                startActivity(intent);
            }
        });
    }
}