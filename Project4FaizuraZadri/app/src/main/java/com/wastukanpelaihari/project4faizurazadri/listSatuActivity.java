package com.wastukanpelaihari.project4faizurazadri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class listSatuActivity extends AppCompatActivity {

    String programmingList[] = {"JAVA","PHP","JAVA SCRIPT","C++","XML","RUBY","PERL","PYTHON"};
    private ListView listSatu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_satu);


        listSatu = (ListView) findViewById(R.id.lisSatu);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, programmingList);
        listSatu.setAdapter(arrayAdapter);

        listSatu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(listSatuActivity.this, "Item click: "+item, Toast.LENGTH_LONG).show();
            }
        });

    }
}
