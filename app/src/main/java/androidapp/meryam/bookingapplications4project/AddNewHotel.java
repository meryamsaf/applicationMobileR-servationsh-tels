package androidapp.meryam.bookingapplications4project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AddNewHotel extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE=101;
    private ImageView imageViewAdd;
    private EditText hotelName,hotelLocation,hotelVille,hotelDetails;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    private Button btnUpload;
    Uri imageUri;
    boolean isImageAdded =false;
    DatabaseReference DataRef;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hotel);

        imageViewAdd=findViewById(R.id.add_image);
        hotelName=findViewById(R.id.hotelName);
        hotelLocation=findViewById(R.id.hotelLocation);
        hotelVille=findViewById(R.id.hotelVille);
        hotelDetails=findViewById(R.id.idd23);
        textViewProgress=findViewById(R.id.textViewProgress);
        progressBar=findViewById(R.id.progressBar);
        btnUpload=findViewById(R.id.saveButton);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        DataRef=FirebaseDatabase.getInstance().getReference().child("hotel");
       storageReference = FirebaseStorage.getInstance().getReference().child("hotelImage");


        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=hotelName.getText().toString();
                final String location=hotelLocation.getText().toString();
                final String ville=hotelVille.getText().toString();
                final String details=hotelDetails.getText().toString();
                if(isImageAdded!=false && name!=null && location!=null && ville!=null && details!=null){
                   uploadImage(name,location,ville,details);
                }
            }
        });


    }

    private void uploadImage( final String name, final String location, final String ville, final String details) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
         final String key =DataRef.push().getKey();
        storageReference.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap =new HashMap();
                        hashMap.put("hotelName",name);
                        hashMap.put("hotelLocation",location);
                        hashMap.put("hotelVille",ville);
                        hashMap.put("hotelDetails",details);
                        hashMap.put("imageUrl",uri.toString());
                        DataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddNewHotel.this,"Data successfully saved!! ",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress+"%");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE && data!=null){
            imageUri=data.getData();
            isImageAdded=true;
            imageViewAdd.setImageURI(imageUri);

        }
    }
}
