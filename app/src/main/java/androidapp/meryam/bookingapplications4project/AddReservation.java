package androidapp.meryam.bookingapplications4project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddReservation extends AppCompatActivity {
private EditText nom,mail,nomHotel,nombrePersonnes,dateReservation,nbrJour;
private int year,month,day;
DatabaseReference DataRef;
Button reservationBtn,showReservations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        nom=findViewById(R.id.clientNom);
        mail=findViewById(R.id.clientMail);
        nomHotel=findViewById(R.id.hotelNom);
        nbrJour=findViewById(R.id.nbrJour);
        reservationBtn=findViewById(R.id.reserverButtn);
        nombrePersonnes=findViewById(R.id.personneNumber);
        showReservations=findViewById(R.id.showReservation);
        dateReservation=findViewById(R.id.reservationDate);
        DataRef= FirebaseDatabase.getInstance().getReference().child("Réservation");

        Calendar calender=Calendar.getInstance();
        dateReservation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                year =calender.get(Calendar.YEAR);
                month=calender.get(Calendar.MONTH);
                day=calender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddReservation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateReservation.setText(SimpleDateFormat.getInstance().format(calender.getTime()));

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // final String key =DataRef.push().getKey();
                final String name=nom.getText().toString();
                final String email=mail.getText().toString();
                final String hotelName=nomHotel.getText().toString();
                final String personneNum=nombrePersonnes.getText().toString();
                final String jourNum= nbrJour.getText().toString();
                final String dateReser=dateReservation.getText().toString();
                Bundle extras=getIntent().getExtras();
                String idUser=extras.getString("idClient");

                HashMap<String,String> reservationHash=new HashMap<>();

                reservationHash.put("NomClient",name);
                reservationHash.put("EmailClient",email);
                reservationHash.put("NomHotel",hotelName);
                reservationHash.put("NombrePersonnes", personneNum);
                reservationHash.put("DateReservation",dateReser);
                reservationHash.put("NombreJour",jourNum);
              // reservationHash.put("idClient",idUser);
                DataRef.push().setValue(reservationHash).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddReservation.this,"Data successfully saved!! ",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        showReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AddReservation.this,ShowReservation.class);
                startActivity(intent);
                finish();
            }
        });
    }
}