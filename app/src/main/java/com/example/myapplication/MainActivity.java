package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Button = (Button) findViewById(R.id.mapsearch);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "kang.db")
                .createFromAsset("database/kang.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainAptmapActivity.class);
                startActivity(intent);
            }
        });

        Button Button2 = (Button) findViewById(R.id.typesearch);
        Button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainAptActivity.class);
                startActivity(intent);
            }
        });


        Button Button3 = (Button) findViewById(R.id.sub);
        Button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("전국 아파트와 주택 매물 정보를 찾아 볼 수 있는 앱 입니다. 현재는 강원도만 지원됩니다.")
                        .setPositiveButton("확인", null)
                        .show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("아니요", null)
                .setNegativeButton("네", (dialog, which) -> {
                    finish();
                })
                .show();
    }

}
