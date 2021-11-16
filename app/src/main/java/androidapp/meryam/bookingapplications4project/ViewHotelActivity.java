package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewHotelActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView hotName,hotCity,hotLocation,hotDetails;
    private Button addBtn;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);
        imageView=findViewById(R.id.single_view_image_Hot);
        hotName=findViewById(R.id.single_view_nomHot);
        hotCity=findViewById(R.id.single_view_villeHotel);
        hotLocation=findViewById(R.id.single_view_hotelLocation);
        hotDetails=findViewById(R.id.single_view_hotelDetails);
        //addBtn=findViewById(R.id.addReservationID);
        ref= FirebaseDatabase.getInstance().getReference().child("hotel");
        String hotelKey=getIntent().getStringExtra("HotelKey");
        ref.child(hotelKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String hotelName=snapshot.child("hotelName").getValue().toString();
                    String hotelVille=snapshot.child("hotelVille").getValue().toString();
                    String hotelLocation=snapshot.child("hotelLocation").getValue().toString();
                    String hotelDetails=snapshot.child("hotelDetails").getValue().toString();
                    String uriImage=snapshot.child("imageUrl").getValue().toString();
                    Picasso.get().load(uriImage).into(imageView);
                    hotName.setText(hotelName);
                    hotCity.setText(hotelVille);
                    hotLocation.setText(hotelLocation);
                    hotDetails.setText(hotelDetails);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}