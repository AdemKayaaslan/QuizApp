package com.ademkayaaslan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Quiz extends AppCompatActivity {
        TextView questionText;
        TextView answerText;
        int questionInt = 0;
        String url;
        JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.answerText);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        DownloadData downloadData = new DownloadData();
        try {
            downloadData.execute(url);
        } catch (Exception e) {}

    }

    public void back (View view) {
        if (questionInt > 0) {questionInt --;}

        try {
            questionText.setText(getQ(questionInt));
            answerText.setText(getA(questionInt));
        } catch (Exception e) {}
        answerText.setVisibility(View.INVISIBLE);
    }

    public void next (View view) {
        if (questionInt < 9){questionInt ++;}
        try {
            questionText.setText(getQ(questionInt));
            answerText.setText(getA(questionInt));
        } catch (Exception e) {}
        answerText.setVisibility(View.INVISIBLE);
    }

    public void answer (View view) {
        answerText.setVisibility(View.VISIBLE);
    }

    private class DownloadData extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpsURLConnection httpsURLConnection;

            try {
                url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                while (data > 0) {
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                return result;
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String results =  jsonObject.getString("results");
                jsonArray = new JSONArray(results);

                try {
                    questionText.setText(getQ(questionInt));
                    answerText.setText(getA(questionInt));
                } catch (Exception e) {}


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private  String getQ (Integer integer) {

        try {
            String selectedQ = jsonArray.getString(integer);
            JSONObject jsonObject1 = new JSONObject(selectedQ);
            String ques = jsonObject1.getString("question");
            return ques;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private  String getA (Integer integer) {

        try {
            String selectedA = jsonArray.getString(integer);
            JSONObject jsonObject1 = new JSONObject(selectedA);
            String ans = jsonObject1.getString("correct_answer");
            return ans;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
