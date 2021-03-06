package local.andregg.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Variables that need to be accessed from the whole class.
    int balance_euro;
    int balance_cent;
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
        final String myUsername = "Angel";
        final String[] friendsItems = {"", "Alice", "Bob", "Charlie", "Dawn", "Elvis", "Frode"};


        //Intialize ArrayList
        transactions = new ArrayList<>();

        //Setup balance
        Random rand = new Random();
        balance_euro = rand.nextInt((109 - 90) + 1) + 90;
        balance_cent = rand.nextInt((99) + 1);
        balance = balance_euro * 100 + balance_cent;
        lblBalance.setText(TransferActivity.calculateBalance(balance));

        //Add first balance transaction to the list.
        transactions.add(TransferActivity.buildTransactionLog(myUsername, TransferActivity.calculateBalance(balance), TransferActivity.calculateBalance(balance)));

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
            I.putExtra("FriendsItems", friendsItems);
            startActivityForResult(I, REQUEST_CODE);
        });

    }

    // This method is invoked when target activity return result data back.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        switch (requestCode)
        {
            case REQUEST_CODE:
                if(resultCode == RESULT_OK)
                {
                    balance = dataIntent.getIntExtra("newBalance", 0);
                    transactions.add(dataIntent.getStringExtra("transactionLog"));
                    lblBalance.setText(TransferActivity.calculateBalance(balance));
                }
        }
    }


}
