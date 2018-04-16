package edu.odu.cs441.sro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button backButton = findViewById(R.id.activity_report_result_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button emailButton = findViewById(R.id.activity_report_result_email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }
}
