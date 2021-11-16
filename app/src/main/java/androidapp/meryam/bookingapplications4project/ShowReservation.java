package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowReservation extends AppCompatActivity {
private RecyclerView reccyclerShow;
private FirebaseDatabase db=FirebaseDatabase.getInstance();
private DatabaseReference root=db.getReference().child("Réservation");
private AdapterShow adapter;
private ArrayList<Reservation> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation);
        reccyclerShow=findViewById(R.id.showRecyclerVie);
        reccyclerShow.setHasFixedSize(true);
        reccyclerShow.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new AdapterShow(this,list);
        reccyclerShow.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    //String documentKey =dataSnapshot.getKey();
                    Reservation reservation=dataSnapshot.getValue(Reservation.class);
                    //reservation.setDocumentKey(documentKey);
                    list.add(reservation);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}