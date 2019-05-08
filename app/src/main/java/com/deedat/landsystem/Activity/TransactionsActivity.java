package com.deedat.landsystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.deedat.landsystem.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class TransactionsActivity extends AppCompatActivity {
Button sell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        sell=findViewById(R.id.sell);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TransactionsActivity.this, SellActivity.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.inflateMenu(R.menu.transactions_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });


    }
}
