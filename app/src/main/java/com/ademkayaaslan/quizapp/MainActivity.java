package com.ademkayaaslan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int categoryInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);

        String[] items = new String[]{"General knowledge", "Animals",  "Entertainment: Film"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryInt = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Choose a category please.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void start (View view) {
        String url;
        if (categoryInt == 0) {
            url = "https://opentdb.com/api.php?amount=10&category=9&type=boolean";
        } else if (categoryInt == 1) {
            url = "https://opentdb.com/api.php?amount=10&category=27&type=boolean";
        } else {
            url = "https://opentdb.com/api.php?amount=10&category=11&type=boolean";
        }
        Intent intent = new Intent(getApplicationContext(),Quiz.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }
}
