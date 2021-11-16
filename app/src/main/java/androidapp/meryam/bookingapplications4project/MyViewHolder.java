package androidapp.meryam.bookingapplications4project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameHot,villeHot;
    View v;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.single_view_image_Hotel);
        nameHot=itemView.findViewById(R.id.single_view_nomHotel);
        villeHot=itemView.findViewById(R.id.single_view_villeHotel);

        v=itemView;

    }
}
