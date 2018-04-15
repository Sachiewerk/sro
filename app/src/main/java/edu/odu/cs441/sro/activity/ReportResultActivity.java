package edu.odu.cs441.sro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import edu.odu.cs441.sro.R;

public class ReportResultActivity extends AppCompatActivity {

    public static String REPORT_RESULT = "REPORT RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_result);

        Intent data = getIntent();
        String result = data.getStringExtra(REPORT_RESULT);

        TextView textView = findViewById(R.id.activity_report_result_textview);

        textView.setText(result);
        Log.d("RESULT", result);
    }
}
