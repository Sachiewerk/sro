package edu.odu.cs441.sro.intent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;

public class EmailReceiptIntent {
    private Context context;
    private String emailAddress;

    public EmailReceiptIntent(Context context) {
        this.context = context;
    }

    public void sendEmail(final Receipt receipt) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.input_prompt_email_address, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = promptsView.findViewById(R.id.input_prompt_email_address_edittext);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                emailAddress = userInput.getText().toString();
                                startEmailIntent(receipt);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                emailAddress = null;
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void startEmailIntent(Receipt receipt) {
        if(emailAddress != null && !emailAddress.isEmpty()) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailAddress});
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
        }
    }
}
