package com.example.vishistvarugeese.dietassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private String imageName;
    private String editImageName;

    private ImageView image;

    private String FILE_DIR = "/storage/emulated/0/Android/data/com.example.vishistvarugeese.dietassistant/files/Pictures/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent editActivityIntent = getIntent();
        imageName = editActivityIntent.getStringExtra("name");

        image = (ImageView) findViewById(R.id.image);

        final String imagePath = FILE_DIR + imageName;
        File imgFile = new  File( FILE_DIR + imageName + ".jpg");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.image);
            myImage.setImageBitmap(myBitmap);

        }

    }


}
