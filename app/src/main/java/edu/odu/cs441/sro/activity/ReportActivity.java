package edu.odu.cs441.sro.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.utility.data.StringPriceParser;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class ReportActivity extends AppCompatActivity {

    private DateTime todayDateTime = new DateTime();
    private DateTime startDateTime = new DateTime();
    private DateTime endDateTime = new DateTime();
    private EditText startDateEditText;
    private EditText endDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initializeDateViews();
        initializeButtons();
    }

    private void initializeButtons() {
        Button generateButton = findViewById(R.id.activity_report_generate_button);
        Button backButton = findViewById(R.id.activity_report_back_button);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReport();
            }
        });
    }

    private void generateReport() {
        final ReceiptViewModel receiptViewModel =
                ViewModelProviders.of(this).get(ReceiptViewModel.class);
        ProgressDialog progressDialog = new ProgressDialog(this);

        new GenerateReportAsyncTask(
                this,
                receiptViewModel,
                startDateTime,
                endDateTime,
                progressDialog
        ).execute();
    }

    private void initializeDateViews() {
        startDateEditText = findViewById(R.id.activity_report_start_date_edittext);
        endDateEditText = findViewById(R.id.activity_report_end_date_edittext);

        startDateEditText.setRawInputType(InputType.TYPE_NULL);
        startDateEditText.setFocusable(true);
        endDateEditText.setRawInputType(InputType.TYPE_NULL);
        endDateEditText.setFocusable(true);


        final DatePickerDialog.OnDateSetListener afterDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                startDateTime = startDateTime.year().setCopy(year);
                startDateTime = startDateTime.monthOfYear().setCopy(monthOfYear + 1);
                startDateTime = startDateTime.dayOfMonth().setCopy(dayOfMonth);
                startDateEditText.setText(startDateTime.toString(DateTimeFormat.shortDate()));
            }
        };

        final DatePickerDialog.OnDateSetListener beforeDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                endDateTime = endDateTime.year().setCopy(year);
                endDateTime = endDateTime.monthOfYear().setCopy(monthOfYear + 1);
                endDateTime = endDateTime.dayOfMonth().setCopy(dayOfMonth);
                endDateEditText.setText(endDateTime.toString(DateTimeFormat.shortDate()));
            }
        };

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ReportActivity.this, afterDateDialog,
                        todayDateTime.getYear(),
                        todayDateTime.getMonthOfYear() - 1,
                        todayDateTime.getDayOfMonth()).show();
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ReportActivity.this, beforeDateDialog,
                        todayDateTime.getYear(),
                        todayDateTime.getMonthOfYear() - 1,
                        todayDateTime.getDayOfMonth()).show();
            }
        });
    }

    private static class GenerateReportAsyncTask extends AsyncTask<Void, Void, Void> {
        WeakReference<Context> contextWeakReference;
        private DateTime startDate;
        private DateTime endDate;
        private ReceiptViewModel receiptViewModel;

        private ProgressDialog progressDialog;

        private HashSet<String> locations = new HashSet<> ();
        private HashSet<String> methods = new HashSet<> ();
        private HashSet<String> categories = new HashSet<> ();

        private StringBuilder stringBuilder = new StringBuilder();

        GenerateReportAsyncTask(Context context,
                                ReceiptViewModel receiptViewModel,
                                DateTime startDate,
                                DateTime endDate,
                                ProgressDialog progressDialog) {
            contextWeakReference = new WeakReference<>(context);
            this.progressDialog = progressDialog;
            this.receiptViewModel = receiptViewModel;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void ... params) {
            ArrayList<Receipt> allReceipts =
                    new ArrayList<> (receiptViewModel.findByDate(startDate.getMillis(), endDate.getMillis()));

            Double totalCost = 0.0;
            Double averageCost = 0.0;
            Integer totalReceipts = allReceipts.size();

            if(totalReceipts == 0) {
                stringBuilder.append("No receipts found for the specified date range");
                return null;
            }

            for(Receipt receipt : allReceipts) {
                Double receiptPrice = receipt.getPrice();
                if(receiptPrice != null) {
                    totalCost += receiptPrice;
                }

                if(receipt.getMethod() != null) {
                    methods.add(receipt.getMethod());
                }

                if(receipt.getLocation() != null) {
                    locations.add(receipt.getLocation());
                }

                if(receipt.getCategory() != null) {
                    categories.add(receipt.getCategory());
                }
            }

            if(allReceipts.size() > 0) {
                averageCost = totalCost / allReceipts.size();
            }


            stringBuilder.append("Summary");
            stringBuilder.append("\n");
            stringBuilder.append("From ");
            stringBuilder.append(startDate.toString(DateTimeFormat.shortDate()));
            stringBuilder.append(" to ");
            stringBuilder.append(endDate.toString(DateTimeFormat.shortDate()));
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("Total number of receipts found: ");
            stringBuilder.append(totalReceipts);
            stringBuilder.append("\n");
            stringBuilder.append("Total Cost: ");
            stringBuilder.append(new StringPriceParser(totalCost).getStringValue());
            stringBuilder.append("\n");
            stringBuilder.append("Average Cost: ");
            stringBuilder.append(new StringPriceParser(averageCost).getStringValue());
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("Cost Breakdown by Location");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            for(String location : locations) {
                ArrayList<Receipt> receipts = new ArrayList<>(receiptViewModel.findByDateAndLocation(
                        startDate.getMillis(),
                        endDate.getMillis(),
                        location
                ));

                Double total = 0.0;
                Double average = 0.0;

                for(Receipt receipt : receipts) {
                    Double price = receipt.getPrice();
                    if(price != null) {
                        total += price;
                    }
                }

                if(receipts.size() > 0) {
                    average = total / receipts.size();
                }

                stringBuilder.append("\t");
                stringBuilder.append(location);
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Number of Receipts: ");
                stringBuilder.append(receipts.size());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Total Cost: ");
                stringBuilder.append(new StringPriceParser(total).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Average Cost: ");
                stringBuilder.append(new StringPriceParser(average).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Cost: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(total / totalCost * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Count: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(receipts.size() / (double)totalReceipts * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\n");
            }
            stringBuilder.append("Cost Breakdown by Category");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            for(String category : categories) {
                ArrayList<Receipt> receipts = new ArrayList<>(receiptViewModel.findByDateAndCategory(
                        startDate.getMillis(),
                        endDate.getMillis(),
                        category
                ));

                Double total = 0.0;
                Double average = 0.0;

                for(Receipt receipt : receipts) {
                    Double price = receipt.getPrice();
                    if(price != null) {
                        total += price;
                    }
                }

                if(receipts.size() > 0) {
                    average = total / receipts.size();
                }

                stringBuilder.append("\t");
                stringBuilder.append(category);
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Number of Receipts: ");
                stringBuilder.append(receipts.size());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Total cost: ");
                stringBuilder.append(new StringPriceParser(total).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Average cost: ");
                stringBuilder.append(new StringPriceParser(average).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Cost: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(total / totalCost * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Count: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(receipts.size() / (double)totalReceipts * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\n");
            }
            stringBuilder.append("Cost Breakdown by Payment Method");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            for(String method : methods) {
                ArrayList<Receipt> receipts = new ArrayList<>(receiptViewModel.findByDateAndMethod(
                        startDate.getMillis(),
                        endDate.getMillis(),
                        method
                ));

                Double total = 0.0;
                Double average = 0.0;

                for(Receipt receipt : receipts) {
                    Double price = receipt.getPrice();
                    if(price != null) {
                        total += price;
                    }
                }

                if(receipts.size() > 0) {
                    average = total / receipts.size();
                }

                stringBuilder.append("\t");
                stringBuilder.append(method);
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Number of Receipts: ");
                stringBuilder.append(receipts.size());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Total cost: ");
                stringBuilder.append(new StringPriceParser(total).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Average cost: ");
                stringBuilder.append(new StringPriceParser(average).getStringValue());
                stringBuilder.append("\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Cost: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(total / totalCost * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\t");
                stringBuilder.append("Percentage of Total Count: ");
                stringBuilder.append(StringPriceParser.toTwoDigitDecimal(receipts.size() / (double)totalReceipts * 100));
                stringBuilder.append("%\n");
                stringBuilder.append("\n");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            progressDialog.dismiss();
            Context context = contextWeakReference.get();
            Intent intent = new Intent(context, ReportResultActivity.class);
            intent.putExtra(ReportResultActivity.REPORT_RESULT, stringBuilder.toString());
            context.startActivity(intent);
        }
    }
}
