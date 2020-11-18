package com.faizuracreator.myintentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnMoveActivity.setOnClickListener(this);

        Button btnMoveActivityData = findViewById(R.id.btn_move_activity_data);
        btnMoveActivityData.setOnClickListener(this);

        Button btnDialPhone = findViewById(R.id.btn_dial_number);
        btnDialPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_move_activity:
                Intent moveIntent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.btn_move_activity_data:
                Intent movewithDataIntent = new Intent(MainActivity.this, MoveActivityData.class);
                movewithDataIntent.putExtra(MoveActivityData.EXTRA_NAME, "DicodingAcademy Boy");
                movewithDataIntent.putExtra(MoveActivityData.EXTRA_AGE, 5);
                startActivity(movewithDataIntent);
                break;

            case R.id.btn_dial_number:
                String phonenumber = "085272667836";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+phonenumber));
                startActivity(dialPhoneIntent);
                break;
        }
    }
}
