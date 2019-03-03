package local.andregg.lab_1;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int balance;
    TextView lblBalance;
    ArrayList<String> transactions;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Variables
        lblBalance = findViewById(R.id.lbl_balance);
        final Button btnTransactions = findViewById(R.id.btn_transactions);
        final Button btnTransfer = findViewById(R.id.btn_transfer);


        //Intialize ArrayList
        transactions = new ArrayList<>();

        //Setup balance
        Random rand = new Random();
        balance = rand.nextInt((110 - 90) + 1) + 90;

        lblBalance.setText(Integer.toString(balance));

        //Add first balance transaction to the list.
        transactions.add(TransferActivity.buildTransactionLog("Angel", balance, balance));
        //Log.d("app1", TransferActivity.buildTransactionLog(username, balance, balance));

        //Transcations button logic
        btnTransactions.setOnClickListener(v -> {
            Intent I = new Intent(MainActivity.this, TransactionActivity.class);
            I.putExtra("TransactionLog", transactions);
            startActivity(I);
        });


        //Transfer button logic
        btnTransfer.setOnClickListener(v -> {
            Intent I = new Intent(MainActivity.this, TransferActivity.class);
            //I.putExtra("TransactionLog", transactions);
            I.putExtra("Balance", balance);
            startActivityForResult(I, REQUEST_CODE);
        });

    }

    // This method is invoked when target activity return result data back.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        Log.d("App1", "onActivityResult");
        switch (requestCode)
        {
            case REQUEST_CODE:
                Log.d("App1", "onActivityResult1");
                if(resultCode == RESULT_OK)
                {
                    Log.d("App1", "onActivityResult2");
                    Intent I = getIntent();
                    balance = dataIntent.getIntExtra("newBalance", 0);
                    transactions.add(dataIntent.getStringExtra("transactionLog"));
                    lblBalance.setText(Integer.toString(balance));
                }
        }
    }


}
