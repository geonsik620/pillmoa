package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MH extends AppCompatActivity {
    ImageView btnB, btnH;
    private LinearLayout buttonLayout; // 동적으로 추가될 버튼 레이아웃
    private Button editButton; // 편집 버튼
    private boolean isEditMode = false; // 편집 모드인지 확인하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh); // mh.xml 연결

        // Find Buttons
        btnB = findViewById(R.id.btnB); // 뒤로가기 버튼
        btnH = findViewById(R.id.btnH); // 홈 버튼

        // 뒤로가기 버튼 클릭 시
        btnB.setOnClickListener(v -> onBackPressed()); // 뒤로가기 기능
        editButton = findViewById(R.id.btnEdit); // 편집 버튼
        buttonLayout = findViewById(R.id.buttonLayout); // 동적으로 버튼 추가할 LinearLayout

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(MH.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });
    }
    private void toggleEditMode() {
        // 편집 모드 변경
        isEditMode = !isEditMode;

        // 편집 모드에 따라 버튼 레이아웃 동적 추가/삭제
        if (isEditMode) {
            // 편집 버튼 텍스트 변경
            editButton.setText("취소");

            // "전체선택" 버튼 생성
            Button selectAllButton = new Button(this);
            selectAllButton.setText("전체선택");
            selectAllButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            selectAllButton.setTextColor(getResources().getColor(R.color.white));
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // "전체선택" 버튼 클릭 동작
                    // 여기에 전체 선택 로직 작성
                }
            });

            // "삭제" 버튼 생성
            Button deleteButton = new Button(this);
            deleteButton.setText("삭제");
            deleteButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            deleteButton.setTextColor(getResources().getColor(R.color.white));
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // "삭제" 버튼 클릭 동작
                    // 여기에 삭제 로직 작성
                }
            });

            // 동적으로 레이아웃에 버튼 추가
            buttonLayout.addView(selectAllButton);
            buttonLayout.addView(deleteButton);
        } else {
            // 편집 모드 종료: 버튼 제거 및 초기 상태로 복구
            editButton.setText("편집");
            buttonLayout.removeAllViews();
        }
    }
}



