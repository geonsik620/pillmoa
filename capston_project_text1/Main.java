package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;



public class Main extends AppCompatActivity {

    TextView Btn1, Btn2, Btn3, Btn4 ,Btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // main.xml 연결

        Btn1 = findViewById(R.id.Btn1); // 의약품 정보 검색
        Btn2 = findViewById(R.id.Btn2); // 처방전 열람
        Btn3 = findViewById(R.id.Btn3); // 복약 알림
        Btn4 = findViewById(R.id.Btn4); // 환경설정
        Btn5 = findViewById(R.id.Btn5); // 검색 이력

        // 의약품 정보 검색 페이지 이동
        Btn1.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, Search.class);
            startActivity(intent);
        });

        // 처방전 열람 페이지 이동 (수정된 부분)
        Btn2.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, PrescriptionViewActivity.class);
            startActivity(intent);
        });

        // 복약 알림 페이지 이동
        Btn3.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, AL.class);
            startActivity(intent);
        });

        // 환경설정 페이지 이동
        Btn4.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, Seting.class);
            startActivity(intent);
        });

        // 검색이력 페이지 이동
        Btn5.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, SearchHistoryActivity.class);
            startActivity(intent);
        });




    }
}
