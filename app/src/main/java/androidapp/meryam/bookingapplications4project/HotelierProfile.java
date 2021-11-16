package androidapp.meryam.bookingapplications4project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HotelierProfile extends AppCompatActivity {
Button btn1,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelier_profile);
        btn1=findViewById(R.id.hotelAdd);
        btn2=findViewById(R.id.hotelReservations);
        btn3=findViewById(R.id.sedeconnecterHotelier);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelierProfile.this, AddNewHotel.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelierProfile.this, ShowReservation.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(HotelierProfile.this, Login_activity.class);
                startActivity(intent);
            }
        });
    }
}