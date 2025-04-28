package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Seting extends AppCompatActivity {

    private ImageView backButton, homeButton, btnH;
    private Button btnPersonalInfo, btnMedicalInfo, btnAppSettings, btnVoiceHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seting);

        // 버튼 및 이미지 뷰 초기화
        backButton = findViewById(R.id.backButton);
        homeButton = findViewById(R.id.btnH);
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo);
        btnAppSettings = findViewById(R.id.btnAppSettings);
        btnVoiceHelp = findViewById(R.id.btnVoiceHelp);
        btnH = findViewById(R.id.btnH); // 홈 버튼

        // 개인 기본정보 버튼 클릭 시 이동
        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seting.this, personal_info.class);
                startActivity(intent);
            }
        });


        // 앱 환경설정 버튼 클릭 시 이동
        btnAppSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seting.this, AppSettings.class);
                startActivity(intent);
            }
        });

        // 음성 도움말 버튼 클릭 시 이동
        btnVoiceHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seting.this, VoiceHelp.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭 시 이전 화면으로 돌아가기
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // 현재 Activity 종료
            }
        });

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Seting.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

        // 홈 버튼 클릭 시 홈 화면으로 이동 (홈 화면 Activity로 가정)
//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
