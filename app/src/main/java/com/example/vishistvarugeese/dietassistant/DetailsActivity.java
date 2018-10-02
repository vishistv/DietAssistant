package com.example.vishistvarugeese.dietassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DetailsActivity extends AppCompatActivity {

    private SessionManager session;

    private String height, weight, age, sex;

    private EditText txtHeight, txtWeight, txtAge;
    private RadioButton radioMale, radioFemale;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Session manager
        session = new SessionManager(getApplicationContext());

        //EditText initialization
        txtHeight = (EditText) findViewById(R.id.txtHeight);
        txtWeight = (EditText) findViewById(R.id.txtWeight);
        txtAge = (EditText) findViewById(R.id.txtAge);

        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        Intent flagIntent = getIntent();
        String flag = flagIntent.getStringExtra("flag");

        if(flag == null)
            flag = "empty";

        if(flag.equalsIgnoreCase("ss")) {
            session.setDetail(false);
        }

        if(session.isDetailSet()){
            Intent intent = new Intent(DetailsActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void getDetails(){
        height = txtHeight.getText().toString().trim();
        weight = txtWeight.getText().toString().trim();
        age = txtAge.getText().toString().trim();
    }


    public void onSubmit(View view) {
        session.setDetail(true);
        getDetails();
        if(radioMale.isChecked())
            sex = "male";
        else if(radioFemale.isChecked())
            sex = "female";

        Intent intent = new Intent(DetailsActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        session.setDetail(true);
    }
}
