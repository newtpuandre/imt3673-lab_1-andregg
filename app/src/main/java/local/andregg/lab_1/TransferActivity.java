package local.andregg.lab_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
        final TextView lblCheck = findViewById(R.id.lbl_amount_check);
        final String balance = I.getExtras().getString("Balance");

        //Split balance into whole euros and cents
        String[] separatedBalance = new String[]{};
        separatedBalance = balance.split("\\.");
        final int balance_euro = Integer.parseInt(separatedBalance[0]);
        final int balance_cent = Integer.parseInt(separatedBalance[1]);

        //Setup spinner
        String[] items = I.getStringArrayExtra("FriendsItems");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v = null;

                // If this is the empty entry, make it hidden
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    // Pass convertView as null to prevent reuse of special case views
                    v = super.getDropDownView(position, null, parent);
                }

                // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FriendsSpinner.setAdapter(adapter);

        btnPay.setEnabled(false);

        txtAmount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Disable button until check is satisfied
                btnPay.setEnabled(false);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Split into whole euro and cents
                if(txtAmount.getText().toString().contains(".")) {
                    String[] tempSepareted = txtAmount.getText().toString().split("\\.");
                    if (tempSepareted.length == 2) {
                        Log.d("app1", txtAmount.getText().toString());
                        int tempEuro = Integer.parseInt(tempSepareted[0]);
                        int tempCent = Integer.parseInt(tempSepareted[1]);

                        if (balance_euro >= tempEuro || balance_cent >= tempCent) {
                        Log.d("app1", "Seems good");
                        }
                    }


                } else {

                }

                if (txtAmount.getText().toString().trim().length() > 0) {
                    btnPay.setEnabled(true);
                    lblCheck.setText("");
                } else {
                    btnPay.setEnabled(false);
                    lblCheck.setText("Amount is outside of bounds.");
                }

            }
        });


        //Transfer button logic
        btnPay.setOnClickListener(v -> {
            if (FriendsSpinner.getSelectedItemPosition() == 0) {
                lblCheck.setText("Please select a recipient.");

            } else {

                //Return to Main Activity with transaction information
                int amount = Integer.parseInt(txtAmount.getText().toString());
                //String newBalance = calculateBalance(balance ,balance_euro, balance_cent);
                //String returnTransaction = buildTransactionLog(FriendsSpinner.getSelectedItem().toString(), amount, newBalance);
                //I.putExtra("newBalance", newBalance);
                //I.putExtra("transactionLog", returnTransaction);
                setResult(RESULT_OK, I);
                finish();
            }

        });

    }

    static public String buildTransactionLog(String user, String transfer, String balance){
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

    static public String calculateBalance(String balance, int balance_euro, int balance_cent) {

        return "test";
    }

}
