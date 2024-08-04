package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button productsCollection = findViewById(R.id.products_collection);

        productsCollection.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ProductCollection.class);
            startActivity(i);
        });
    }
}
