package com.example.myfood.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfood.Class.Group;
import com.example.myfood.Class.Picture;
import com.example.myfood.Class.User;
import com.example.myfood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Camera extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public boolean isPicExists = false;
    private Bitmap photo;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private DatabaseReference dbGroup;
    private DatabaseReference dbPicture;
    private FirebaseAuth mAuth;
    public User user;
    public Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        Intent intent = getIntent();
        if(intent != null){
            this.user = (User) intent.getExtras().getSerializable(Login.LOGIN_USER_KEY);
            this.group = (Group) intent.getExtras().getSerializable(Login.LOGIN_GROUP_KEY);
        }
        this.dbGroup = FirebaseDatabase.getInstance().getReference("Groups").child(this.group.getGroupName());
        this.dbPicture = FirebaseDatabase.getInstance().getReference("Picture");


        Button savePic = (Button) this.findViewById(R.id.button_save);
        savePic.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {

               if (isPicExists){
                   Date dt = Calendar.getInstance().getTime();
                   SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                   String formattedDate = df.format(dt);

                   ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                   photo.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                   String imageEncoded = Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT);
                   int picHashCode = this.hashCode();
                   Picture picture = new Picture(imageEncoded, formattedDate, user, group, picHashCode);

                   group.addPictureId(picHashCode);
                   dbGroup.setValue(group);
                   dbPicture.push().setValue(picture);
                   Toast.makeText(getBaseContext(), "התמונה נשמרה בהצלחה", Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(getBaseContext(), "אנא צלם תמונה", Toast.LENGTH_LONG).show();
               }
               finish();
            }
        });

        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            this.photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            this.isPicExists = true;
        }
    }
}
