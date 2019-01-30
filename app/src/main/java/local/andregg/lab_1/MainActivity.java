package local.andregg.lab_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView lblBalance = findViewById(R.id.lbl_balance);

        Random rand = new Random();
        int randomNum = rand.nextInt((110 - 90) + 1) + 90;

        lblBalance.setText(Integer.toString(randomNum));

    }
}
