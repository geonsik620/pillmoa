package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PrescriptionViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);

        // QR 코드 검색 버튼
        Button qrCodeButton = findViewById(R.id.qrCodeButton);
        qrCodeButton.setOnClickListener(v -> showHelpDialog("QR 코드 검색 기능입니다."));

        // OCR 검색 버튼
        Button ocrButton = findViewById(R.id.ocrButton);
        ocrButton.setOnClickListener(v -> showHelpDialog("OCR 검색 기능입니다."));

        // 사진 OCR 검색 버튼
        Button photoOcrButton = findViewById(R.id.photoOcrButton);
        photoOcrButton.setOnClickListener(v -> showHelpDialog("사진 OCR 검색 기능입니다."));

        // 뒤로가기 버튼
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        // 홈 버튼
        ImageView homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            // Main 화면으로 이동
            Intent intent = new Intent(PrescriptionViewActivity.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    // 도움말 다이얼로그 표시 함수
    private void showHelpDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("도움말")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        // Back 버튼 클릭 시 이전 화면으로 이동
        super.onBackPressed();
    }
}
