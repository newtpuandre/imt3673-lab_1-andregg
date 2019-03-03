package local.andregg.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        //Handle incoming
        Intent I = getIntent();

        ArrayList<String> transactions = I.getExtras().getStringArrayList("TransactionLog");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, transactions);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLongItemClick(View view, int position) {
        String[] separated = adapter.getItem(position).split(" | ");
        Toast.makeText(this, separated[2] + " " + separated[4], Toast.LENGTH_SHORT).show();
    }
}
