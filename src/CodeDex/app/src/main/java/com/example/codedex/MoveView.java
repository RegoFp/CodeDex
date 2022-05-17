package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.codedex.models.MoveData;

import org.parceler.Parcels;

public class MoveView extends AppCompatActivity {

    private MoveData moveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);

        Intent intent = getIntent();
        moveData = Parcels.unwrap(getIntent().getParcelableExtra("move"));

    }
}