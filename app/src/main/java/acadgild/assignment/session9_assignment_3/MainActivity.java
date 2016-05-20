package acadgild.assignment.session9_assignment_3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnShowDialog;

    Context context;

    LayoutInflater inflater;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    View alrtDialogView;

    EditText etInput;
    Button btnOk, btnCancel;

    AsyncTaskProcess asyncTaskProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowDialog = (Button) findViewById(R.id.btnShowAlertDialog);

        btnShowDialog.setOnClickListener(this);

        context = MainActivity.this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnShowAlertDialog) {
            builder = new AlertDialog.Builder(this);
            dialog = builder.create();
            inflater = LayoutInflater.from(this);
            alrtDialogView = inflater.inflate(R.layout.user_input_alertdialog, null, false);

            etInput = (EditText) alrtDialogView.findViewById(R.id.etInputNumber);
            btnOk = (Button) alrtDialogView.findViewById(R.id.btnOk);
            btnCancel = (Button) alrtDialogView.findViewById(R.id.btnCancel);

            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //builder.setView(alrtDialogView).show();
            dialog.setView(alrtDialogView);
            dialog.show();

        } else if (v.getId() == R.id.btnOk) {

            int runningTime = Integer.parseInt(etInput.getText().toString());

            dialog.dismiss();
            asyncTaskProcess = new AsyncTaskProcess(this, runningTime);
            asyncTaskProcess.execute();
        } else if (v.getId() == R.id.btnCancel) {
            dialog.dismiss();
        }
    }
}

class AsyncTaskProcess extends AsyncTask<Void, Integer, Void> {

    int numCounter;
    ProgressDialog progressDialog;
    Context context;


    public AsyncTaskProcess(Context context, int numCounter) {
        this.numCounter = numCounter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "ProgressDialog", "Wait!...");

    }

    @Override
    protected Void doInBackground(Void... params) {


        for (int i = numCounter; i >= 0; i--) {
            try {
                Thread.sleep(1000);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage("Please wait! for " + String.valueOf(values[0]) + " Seconds ....");

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);


        alertBuilder.setMessage("Completed");
        alertBuilder.setTitle("Successfully");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();

        alertDialog.show();


    }
}

