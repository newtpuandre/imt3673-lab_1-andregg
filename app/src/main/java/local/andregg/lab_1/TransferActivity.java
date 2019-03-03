package local.andregg.lab_1;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //Handle incoming data
        Intent I = getIntent();

        //Variables
        final EditText txtAmount = findViewById(R.id.txt_amount);
        final Spinner FriendsSpinner = findViewById(R.id.spinner_friends);
        final Button btnPay = findViewById(R.id.btn_pay);
        final TextView btnCheck = findViewById(R.id.lbl_amount_check);

        final int balance = I.getExtras().getInt("Balance");
        final String myUsername = "Angel";


        //Setup spinner

        String[] items = new String[]{"Alice", "Bob", "Charlie", "Dawn", "Elvis", "Frode"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        FriendsSpinner.setAdapter(adapter);

        btnPay.setEnabled(false);

        txtAmount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Disable button until check is satisfied
                btnPay.setEnabled(false);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txtAmount.getText().toString().trim().length() > 0 && Integer.parseInt(txtAmount.getText().toString()) <= balance) {
                    btnPay.setEnabled(true);
                } else {
                    btnPay.setEnabled(false);
                }

            }
        });


        //Transfer button logic
        btnPay.setOnClickListener(v -> {
            //Return to Main Activity with transaction information
            int amount = Integer.parseInt(txtAmount.getText().toString());
            int newBalance = balance-amount;
            String returnTransaction = buildTransactionLog(FriendsSpinner.getSelectedItem().toString(), amount, newBalance);
            I.putExtra("newBalance", newBalance);
            I.putExtra("transactionLog", returnTransaction);
            setResult(RESULT_OK, I);
            finish();
        });

    }

    static public String buildTransactionLog(String user, int transfer, int balance){
        StringBuilder sb = new StringBuilder();
        sb.append(TransferActivity.getCurTime());
        sb.append(" | ");
        sb.append(user);
        sb.append(" | ");
        sb.append(transfer);
        sb.append(" | ");
        sb.append(balance);

        return (sb.toString());
    }

    static public String getCurTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

}