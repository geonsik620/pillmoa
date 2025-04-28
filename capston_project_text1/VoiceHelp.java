package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class VoiceHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voicehelp);

        // 상단 뒤로가기 버튼
        ImageView backButton = findViewById(R.id.backButton);
        ImageView btnH = findViewById(R.id.btnH);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // 현재 화면을 종료하여 이전 화면으로 돌아감
            }
        });

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(VoiceHelp.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

        // 버튼 연결
        Button buttonIntro = findViewById(R.id.buttonIntro);
        Button buttonSearchInfo = findViewById(R.id.buttonSearchInfo);
        Button buttonPrescription = findViewById(R.id.buttonPrescription);
        Button buttonSearchHistory = findViewById(R.id.buttonSearchHistory);
        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonInquiry = findViewById(R.id.buttonInquiry);

        // 각 버튼 클릭 시 해당 페이지 번호 전달
        buttonIntro.setOnClickListener(v -> navigateToTextHelpPage(0));
        buttonSearchInfo.setOnClickListener(v -> navigateToTextHelpPage(1));
        buttonPrescription.setOnClickListener(v -> navigateToTextHelpPage(2));
        buttonSearchHistory.setOnClickListener(v -> navigateToTextHelpPage(3));
        buttonSettings.setOnClickListener(v -> navigateToTextHelpPage(4));
        buttonInquiry.setOnClickListener(v -> navigateToTextHelpPage(5));
    }

    // TextHelp로 이동하며 페이지 번호 전달
    private void navigateToTextHelpPage(int page) {
        Intent intent = new Intent(this, Texthelp.class); // 정확한 클래스 이름 확인
        intent.putExtra("page", page); // 페이지 번호 전달
        startActivity(intent);
    }

}