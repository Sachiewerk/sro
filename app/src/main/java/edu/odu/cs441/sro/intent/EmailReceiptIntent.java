package edu.odu.cs441.sro.intent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;

public class EmailReceiptIntent {
    private Context context;

    public EmailReceiptIntent(Context context) {
        this.context = context;
    }

    public void sendEmail(Receipt receipt) {

        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(
                    android.content.Intent.EXTRA_SUBJECT,
                    "Receipt Photo from SRO: " + receipt.getTitle());
            emailIntent.putExtra(
                    android.content.Intent.EXTRA_TEXT,
                    "Here is the photo of receipt: " + receipt.getTitle());
            emailIntent.putExtra(
                    Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(
                            context,
                            "edu.odu.cs441.sro",
                            new File(receipt.getImageFilePath())
                    )
            );
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch(Exception e) {
            Toast.makeText(context, "Error retrieving image file", Toast.LENGTH_SHORT).show();
        }
    }
}
