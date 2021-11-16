package androidapp.meryam.bookingapplications4project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterShow extends RecyclerView.Adapter<AdapterShow.MyViewHolder> {
ArrayList<Reservation> mList;
Context context;
DatabaseReference  ref,DataRef;

public AdapterShow(Context context,ArrayList<Reservation>mList){
    this.mList=mList;
    this.context=context;

}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemshowreservations,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      Reservation reservation=mList.get(position);
      holder.NomHotel.setText(reservation.getNomHotel());
      holder.DateReservation.setText(reservation.getDateReservation());
      holder.NombreJour.setText(reservation.getNombreJour());
      holder.editbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.NomHotel.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200).create();


              View view=dialogPlus.getHolderView();
              EditText hotelN=view.findViewById(R.id.updateHotel);
              EditText dateR=view.findViewById(R.id.updateDate);
              EditText nbrJ=view.findViewById(R.id.updateNbrJour);
              Button btnUpadate=view.findViewById(R.id.updateBtn);
             hotelN.setText(reservation.getNomHotel());
             dateR.setText(reservation.getDateReservation());
             nbrJ.setText(reservation.getNombreJour());
              dialogPlus.show();

              btnUpadate.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Map<String,Object> map=new HashMap<>();
                     map.put("NomHotel",hotelN.getText().toString());
                     map.put("NombreJour",nbrJ.getText().toString());
                    map.put("DateReservation",dateR.getText().toString());
                    //String key=FirebaseDatabase.getInstance().getReference().getKey() ;
                    FirebaseDatabase.getInstance().getReference().child("Réservation")
                            .child(String.valueOf(getItemId(position)))
                            .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(holder.NomHotel.getContext(),"Updated successfully",Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(holder.NomHotel.getContext(),"Error while updating!!",Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        }
                    });
                  }
              });
          }
      });
      holder.deletebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder builder=new AlertDialog.Builder(holder.NomHotel.getContext());
              builder.setTitle("Are you sure?");
              builder.setMessage("Deleted data can't be undo!");

              builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Réservation")
                       .child(String.valueOf(getItemId(position))).removeValue();
                  }
              });

              builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Toast.makeText(holder.NomHotel.getContext(),"Cancelled!!",Toast.LENGTH_SHORT).show();
                  }
              });
              builder.show();
          }
      });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView NomHotel,DateReservation,NombreJour;
        Button deletebtn,editbtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deletebtn=itemView.findViewById(R.id.showSupprimer);
            editbtn=itemView.findViewById(R.id.showModifier);
            NomHotel=itemView.findViewById(R.id.hotelShow);
            DateReservation=itemView.findViewById(R.id.dateShow);
            NombreJour=itemView.findViewById(R.id.jourShow);
        }
    }
}
