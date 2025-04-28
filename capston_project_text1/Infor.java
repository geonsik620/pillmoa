package com.example.capston_project_text1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Infor extends AppCompatActivity {
    String itemName;
    TextView queryTextView;
    Button inbtn1, nbtn, nbtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infor);

        // 전달된 데이터
        itemName = getIntent().getStringExtra("itemName");

        // TextView 초기화
        queryTextView = findViewById(R.id.tv);
        if (itemName != null) {
            queryTextView.setText(itemName);
        } else {
            queryTextView.setText("의약품 정보를 불러올 수 없습니다.");
        }

        // 기본 정보 버튼
        inbtn1 = findViewById(R.id.inbtn1);
        inbtn1.setOnClickListener(v -> {
            Intent intent = new Intent(Infor.this, Basic.class);
            intent.putExtra("itemName", itemName);
            startActivity(intent);
        });

        // 뒤로가기 버튼 설정
        ImageView btnB = findViewById(R.id.btnB);
        btnB.setOnClickListener(v -> {
            finish(); // 현재 액티비티를 종료하여 이전 액티비티 상태 유지
        });
        nbtn = (Button) findViewById(R.id.nbtn);
        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsUrl = "https://search.naver.com/search.naver?where=news&query=" + itemName;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
                startActivity(browserIntent);
            }
        });
        nbtn2 = (Button)findViewById(R.id.nbtn2);
        nbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsUrl = "https://search.naver.com/search.naver?where=news&query=" + itemName + " 부작용";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
                startActivity(browserIntent);
            }
        });
        ImageView btnH = findViewById(R.id.btnH);
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Infor.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
