package com.larizon.prabu;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    TextView register, forgot;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        register = (TextView) findViewById(R.id.register);
        forgot = (TextView) findViewById(R.id.forgot);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Forgot.class);
                startActivity(i);
            }
        });


    }
}
