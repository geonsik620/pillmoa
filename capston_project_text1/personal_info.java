package com.example.capston_project_text1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class personal_info extends AppCompatActivity {

    private RadioGroup visionGroup, genderGroup, pregnancyGroup;
    private Spinner yearSpinner;
    private Button saveButton;
    private ImageView btnH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

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
            Intent intent = new Intent(personal_info.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

        // View 초기화
        visionGroup = findViewById(R.id.visionGroup);
        genderGroup = findViewById(R.id.genderGroup);
        pregnancyGroup = findViewById(R.id.pregnancyGroup);
        yearSpinner = findViewById(R.id.yearSpinner);
        saveButton = findViewById(R.id.saveButton);
        btnH = findViewById(R.id.btnH); // 홈 버튼

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(personal_info.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

        // 연도 스피너 설정
        setupYearSpinner();

        // 저장 버튼 클릭 이벤트
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonalInfo();
            }
        });
    }

    /**
     * 연도 스피너 설정
     */
    private void setupYearSpinner() {
        List<String> years = new ArrayList<>();
        for (int i = 1900; i <= 2024; i++) {
            years.add(i + "년");
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // 기본적으로 현재 년도를 선택하도록 설정
        int currentYear = 2000; // 현재 년도 (여기서 2024로 설정, 실제로는 현재 년도를 동적으로 계산)
        int position = years.indexOf(currentYear + "년"); // 현재 년도에 해당하는 위치
        if (position != -1) {
            yearSpinner.setSelection(position); // 기본 선택 항목으로 설정
        }
    }

    /**
     * 개인 정보 저장
     */
    private void savePersonalInfo() {
        // 사용자 입력값 가져오기
        String birthYear = yearSpinner.getSelectedItem().toString().replace("년", "");
        int visionStatus = getSelectedRadioValue(visionGroup, R.id.vision_yes, R.id.vision_no);
        int gender = getSelectedRadioValue(genderGroup, R.id.gender_male, R.id.gender_female);
        Integer pregnancyStatus = getOptionalRadioValue(pregnancyGroup, R.id.pregnancy_yes, R.id.pregnancy_no);

        // 필수 입력값 체크
        if (birthYear.isEmpty() || visionStatus == -1 || gender == -1) {
            Toast.makeText(this, "모든 필수 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 데이터베이스 저장
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean isInserted = DatabaseHelper.insertPersonalInfo(db, birthYear, visionStatus, gender, pregnancyStatus);

        if (isInserted) {
            Toast.makeText(this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "데이터 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 필수 RadioGroup의 선택값을 가져옴
     *
     * @param group  RadioGroup 객체
     * @param yesId  "예" 버튼 ID
     * @param noId   "아니오" 버튼 ID
     * @return 선택된 값 (1: 예, 0: 아니오), 선택 안 됨 시 -1
     */
    private int getSelectedRadioValue(RadioGroup group, int yesId, int noId) {
        int checkedId = group.getCheckedRadioButtonId();
        if (checkedId == yesId) return 1;
        if (checkedId == noId) return 0;
        return -1;
    }

    /**
     * 선택적 RadioGroup의 선택값을 가져옴
     *
     * @param group  RadioGroup 객체
     * @param yesId  "예" 버튼 ID
     * @param noId   "아니오" 버튼 ID
     * @return 선택된 값 (1: 예, 0: 아니오), 선택 안 됨 시 null
     */
    private Integer getOptionalRadioValue(RadioGroup group, int yesId, int noId) {
        int checkedId = group.getCheckedRadioButtonId();
        if (checkedId == yesId) return 1;
        if (checkedId == noId) return 0;
        return null;
    }
}