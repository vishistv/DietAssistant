package com.example.vishistvarugeese.dietassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class DashBoardActivity extends AppCompatActivity {

    TextView txtLinearProgressValue;
    TextView txtTimeToEat;

    CircularProgressBar circularProgressBar1;
    CircularProgressBar circularProgressBar3;
    CircularProgressBar circularProgressBar2;

    GraphView graph;

    ProgressBar progressBar;

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private String imageName;
    private String mCurrentPhotoPath;

    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        graph = (GraphView) findViewById(R.id.graph);

        circularProgressBar1 = (CircularProgressBar) findViewById(R.id.circularProgress1);
        circularProgressBar2 = (CircularProgressBar) findViewById(R.id.circularProgress2);
        circularProgressBar3 = (CircularProgressBar) findViewById(R.id.circularProgress3);

        txtLinearProgressValue = (TextView) findViewById(R.id.txtLinearProgressValue);
        txtTimeToEat = (TextView) findViewById(R.id.txtTimeToEat);

        //Navigation Drawer
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.navgation);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpDrawerContent(nvDrawer);

        Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread= new Thread(myRunnableThread);
        myThread.start();

        setLineGraph();
        setCircularProgressBar();
    }

    public void setLineGraph(){
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 4),
                new DataPoint(6, 7),
                new DataPoint(8, 3)
        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(2, 5),
                new DataPoint(4, 7),
                new DataPoint(6, 2),
                new DataPoint(7, 6)
        });
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(2, 3),
                new DataPoint(5, 4),
                new DataPoint(6, 2),
                new DataPoint(9, 6)
        });

        series1.setTitle("Calories");
        series1.setColor(getResources().getColor(R.color.progress1));
        series1.setDrawDataPoints(true);
        series1.setDataPointsRadius(10);
        series1.setThickness(8);

        series2.setTitle("Protiens");
        series2.setColor(getResources().getColor(R.color.progress2));
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);

        series3.setTitle("Fat");
        series3.setColor(getResources().getColor(R.color.progress3));
        series3.setDrawDataPoints(true);
        series3.setDataPointsRadius(10);
        series3.setThickness(8);

        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.addSeries(series3);
    }

    public void setCircularProgressBar(){
        circularProgressBar1.setProgressColor(getResources().getColor(R.color.progress1));
        circularProgressBar1.setProgressWidth(15);

        circularProgressBar2.setProgressColor(getResources().getColor(R.color.progress2));
        circularProgressBar2.setProgressWidth(15);

        circularProgressBar3.setProgressColor(getResources().getColor(R.color.progress3));
        circularProgressBar3.setProgressWidth(15);

        circularProgressBar1.setProgress(94);
        circularProgressBar2.setProgress(90);
        circularProgressBar3.setProgress(75);
    }

    public void setHorizonatalProgressBar(String strMessage, int progressPercentage){
        progressBar.setProgress(progressPercentage);
        txtLinearProgressValue.setText(progressPercentage + "%");
        txtTimeToEat.setText(strMessage);
    }

    public void getTimeToEat(int curHour, int curMin){
        int curTime = (curHour * 100) + curMin;

        if( curTime > 900 & curTime < 1400){
            int linearProgressValue = (curTime - 900) / 5;
            int timeLeft = 14 - curHour;
            String strTimeLeft = "Have your lunch in " + timeLeft + " hours";
            setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

        } else if( curTime > 1400 && curTime < 2200) {
            int linearProgressValue = ( curTime - 1400) / 8;
            int timeLeft = 22 - curHour;
            String strTimeLeft = "Have your dinner in " + timeLeft + " hours";
            setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

        } else if( curTime == 900 ) {
            int linearProgressValue = 100;
            Log.d("progreeValue", linearProgressValue + "3");
            String strTimeLeft = "Time to have your breakfast";
            setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

        } else if( curTime == 1400 ) {
            int linearProgressValue = 100;
            String strTimeLeft = "Time to have your lunch";
            setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

        } else if( curTime == 2200 ) {
            int linearProgressValue = 100;
            String strTimeLeft = "Time to have your dinner";
            setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

        } else {
            int diffTime = 2200 - curTime;
            if(diffTime < 0){
                int linearProgressValue = ( diffTime + 2400) / 11;
                int timeLeft = 9 - curHour;
                String strTimeLeft = "Have your dinner in " + timeLeft + " hours";
                setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

            } else {
                int linearProgressValue = diffTime / 11;
                int timeLeft = 9 - curHour + 24;
                String strTimeLeft = "Have your dinner in " + timeLeft + " hours";
                setHorizonatalProgressBar(strTimeLeft, linearProgressValue);

            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    Date dt = new Date();
                    int curHour = dt.getHours();
                    int curMin = dt.getMinutes();
                    getTimeToEat(curHour, curMin);
                }catch (Exception e) {}
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });
    }

    public void selectItemDrawer(MenuItem menuitem){
        switch (menuitem.getItemId()){
            case R.id.reenter_details:
                Intent intentReenterDetails = new Intent(DashBoardActivity.this , DetailsActivity.class);
                intentReenterDetails.putExtra("flag", "ss");
                startActivity(intentReenterDetails);
                break;
            case R.id.chatbot:
                Intent intentCreateUser = new Intent(DashBoardActivity.this , ChatBotActivity.class);
                startActivity(intentCreateUser);
                break;
            case R.id.camera:
                takePicture();
                break;
            case R.id.diet_plan:
                Intent intentUserAccountsData = new Intent(DashBoardActivity.this , DietPlanActivity.class);
                startActivity(intentUserAccountsData);
                break;
            case R.id.contact:

            default:
                break;
        }
    }

    class CountDownRunner implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

    //region Take pictures
    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("FILE CREATION ERROR", ex + "");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider1",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "picture";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        imageName = image.getName();
        imageName = imageName.substring(0,imageName.length()-4);

        Log.d("mCurrent", imageName);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("mCurrentPhoto", mCurrentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap thumbnail = null;
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Intent i = new Intent(this, CameraActivity.class);
            i.putExtra("name", imageName);
            startActivity(i);
        }
    }
    //endregion
}
