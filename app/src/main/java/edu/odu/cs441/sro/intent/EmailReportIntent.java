package edu.odu.cs441.sro.intent;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class EmailReportIntent {
    private Context context;

    public EmailReportIntent(Context context) {
        this.context = context;
    }

    public void sendEmail(String message) {

        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/html");
            emailIntent.putExtra(
                    android.content.Intent.EXTRA_SUBJECT,
                    "Report from SRO"
            );
            emailIntent.putExtra(
                    android.content.Intent.EXTRA_TEXT,
                    "--- Start of Report ---\n" + message);
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch(Exception e) {
            Toast.makeText(context, "Error Sending Email", Toast.LENGTH_SHORT).show();
        }
    }
}
