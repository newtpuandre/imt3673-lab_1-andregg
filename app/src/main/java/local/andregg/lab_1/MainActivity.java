package local.andregg.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Variables
        final TextView lblBalance = findViewById(R.id.lbl_balance);

        final Button btnTransactions = findViewById(R.id.btn_transactions);
        final Button btnTransfer = findViewById(R.id.btn_transfer);

        //Setup balance
        Random rand = new Random();
        balance = rand.nextInt((110 - 90) + 1) + 90;

        lblBalance.setText(Integer.toString(balance));

        //Transcations button logic
        btnTransactions.setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, TransactionActivity.class));
                });


        //Transfer button logic
        btnTransfer.setOnClickListener(v -> {

            // We start a new activity with the previously created intent.
            startActivity(new Intent(MainActivity.this, TransferActivity.class));
        });

    }
}
