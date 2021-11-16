package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;

public class HomeActivity extends AppCompatActivity {
   private EditText inputSearch;
   private RecyclerView recyclerView;
   private FloatingActionButton floatingBtn;
   FirebaseRecyclerOptions<Hotel> options;
   FirebaseRecyclerAdapter<Hotel,MyViewHolder>adapter;
   DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataRef= FirebaseDatabase.getInstance().getReference().child("hotel");
        inputSearch=findViewById(R.id.inputSearch);
        recyclerView=findViewById(R.id.recyclerViewId);
        floatingBtn=findViewById(R.id.floatingBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNewHotel.class));
            }
        });

        LoadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    LoadData(s.toString());
                }else{
                    LoadData("");
                }
            }
        });

    }

    private void LoadData(String data) {
        Query query=DataRef.orderByChild("hotelVille").startAt(data).endAt(data+"\uf8ff");
     options=new FirebaseRecyclerOptions.Builder<Hotel>().setQuery(query,Hotel.class).build();
     adapter=new FirebaseRecyclerAdapter<Hotel, MyViewHolder>(options) {
         @Override
         protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Hotel model) {
             Picasso.get().load(model.getImageUrl()).into(holder.imageView);
           holder.nameHot.setText(model.getHotelName());
           holder.villeHot.setText(model.getHotelVille());
           holder.v.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(HomeActivity.this,ViewHotelActivity.class);
                    intent.putExtra("HotelKey",getRef(position).getKey());
                    startActivity(intent);
               }
           });



         }

         @NonNull
         @Override
         public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
             return new MyViewHolder(v);
         }
     };
     adapter.startListening();
     recyclerView.setAdapter(adapter);

     
    }
}