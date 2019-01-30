package local.andregg.lab_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //Variables
        final Spinner FriendsSpinner = findViewById(R.id.spinner_friends);

        //Setup spinner

        String[] items = new String[]{"Alice", "Bob", "Charlie", "Dawn", "Elvis", "Frode"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        FriendsSpinner.setAdapter(adapter);
    }
}
