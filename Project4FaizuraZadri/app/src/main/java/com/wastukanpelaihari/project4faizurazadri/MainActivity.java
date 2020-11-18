package com.wastukanpelaihari.project4faizurazadri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnListSatu,btnListDua,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListSatu = (Button) findViewById(R.id.btnListSatu);
        btnListDua = (Button) findViewById(R.id.btnListDua);
        btnExit = (Button) findViewById(R.id.btnEXit);


        btnListSatuAction();
        btnListDuaAction();
        btnExitAction();
    }

    private void btnExitAction() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Confirm Exit..!!!");
                alertDialogBuilder.setIcon(R.drawable.logo1);
                alertDialogBuilder.setMessage("Are you sure, You want to exit");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You clicked over No", Toast.LENGTH_LONG).show();
                    }
                });


                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void btnListDuaAction() {
        btnListSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlistSatu = new Intent(MainActivity.this, listSatuActivity.class);
                startActivity(intentlistSatu);
            }
        });
    }

    private void btnListSatuAction() {
        
    }

}
