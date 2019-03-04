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
        final int balance = I.getExtras().getInt("Balance");

        //Setup spinner
        String[] items = I.getStringArrayExtra("FriendsItems");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items){
            //Make so the first item in the dropdown is hidden since this is the dummy item.
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v; // = null

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

        //Disable button by default
        btnPay.setEnabled(false);

        //TextWatcher for checking amount in txtAmount Edittext
        txtAmount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Disable button until check is satisfied
                btnPay.setEnabled(false);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Convert to a whole int
                if (txtAmount.getText().toString().length() > 0) {
                    int calculatedRequest = calculateRequest(txtAmount.getText().toString());

                    if ( calculatedRequest <= balance) { //Is inserted amount withing the bounds
                        btnPay.setEnabled(true);
                        lblCheck.setText("");
                    } else {
                        btnPay.setEnabled(false);
                        lblCheck.setText("Amount is outside of bounds.");
                    }
                } else {
                    btnPay.setEnabled(false);
                }

            }
        });


        //Transfer button logic
        btnPay.setOnClickListener(v -> {
            if (FriendsSpinner.getSelectedItemPosition() == 0) {
                lblCheck.setText("Please select a recipient.");

            } else {

                //Return to Main Activity with transaction information
                int calculatedRequest = calculateRequest(txtAmount.getText().toString());

                int newBalance = balance - calculatedRequest;
                String returnTransaction = buildTransactionLog(FriendsSpinner.getSelectedItem().toString(),
                        calculateBalance(calculatedRequest),  calculateBalance(newBalance));
                I.putExtra("newBalance", newBalance);
                I.putExtra("transactionLog", returnTransaction);
                setResult(RESULT_OK, I);
                finish();
            }

        });

    }

    //Builds the transaction log.
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

    //Gets current timestamp
    static public String getCurTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    //Calculates from a whole integer into a string containing euros and cents
    static public String calculateBalance(int balance) {
        String ret; //Return string

        int euro = balance / 100;
        int cent = balance % 100;

        if (Integer.parseInt(String.valueOf(cent)) < 10 ) { //Add a zero to make value correct
            ret = String.valueOf(euro) + ".0" + String.valueOf(cent);
        } else {
            ret = String.valueOf(euro) + "." + String.valueOf(cent);
        }

        return ret;
    }

    //Calculate requested transfer based on input string.
    static public int calculateRequest (String value) {
        int calculatedRequest;
        String[] separated = value.split("\\.");
        if (separated.length == 2) { //Actually got cents
            char firstDigit = separated[1].charAt(0);
            if(Integer.parseInt(String.valueOf(firstDigit)) == 0) { //Number is below 10
                calculatedRequest = Integer.parseInt(separated[0]) * 100 + Integer.parseInt(separated[1]);
            } else { //Number is a multiple of 10
                calculatedRequest = Integer.parseInt(separated[0]) * 100 + (Integer.parseInt(separated[1]) * 10);
            }
        } else { //Calculate whole euros
            calculatedRequest = Integer.parseInt(separated[0]) * 100;
        }

        return calculatedRequest;
    }

}
