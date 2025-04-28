package com.example.capston_project_text1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlramAdd extends AppCompatActivity {
    private static final int REQUEST_CODE_SET_TIME = 1;
    private static final String PREF_NAME = "AlarmPreferences";
    private static final String KEY_ALARM_TIME = "alarm_time";
    private static final String KEY_REPEAT_DETAILS = "alarm_repeat";

    // 기존 변수들
    TextView alarmText;
    Button btnSave, btnConfirmRepeat;
    ImageView btnB;

    // 추가된 변수들
    RadioGroup repeatGroup;
    RadioButton noRepeat, hourlyRepeat, weeklyRepeat;
    LinearLayout timeRepeatContainer, weeklyRepeatContainer;
    NumberPicker timeRepeatPicker, minuteRepeatPicker;
    CheckBox checkMonday, checkTuesday, checkWednesday, checkThursday, checkFriday, checkSaturday, checkSunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alram_add);

        // 기존 뷰 초기화
        alarmText = findViewById(R.id.alarmText);
        btnSave = findViewById(R.id.btnS);
        btnB = findViewById(R.id.btnB);

        // 추가된 뷰 초기화
        repeatGroup = findViewById(R.id.repeatGroup);
        noRepeat = findViewById(R.id.noRepeat);
        hourlyRepeat = findViewById(R.id.hourlyRepeat);
        weeklyRepeat = findViewById(R.id.weeklyRepeat);
        timeRepeatContainer = findViewById(R.id.timeRepeatContainer);
        weeklyRepeatContainer = findViewById(R.id.weeklyRepeatContainer);
        timeRepeatPicker = findViewById(R.id.timeRepeatPicker);
        minuteRepeatPicker = findViewById(R.id.minuteRepeatPicker);
        checkMonday = findViewById(R.id.checkMonday);
        checkTuesday = findViewById(R.id.checkTuesday);
        checkWednesday = findViewById(R.id.checkWednesday);
        checkThursday = findViewById(R.id.checkThursday);
        checkFriday = findViewById(R.id.checkFriday);
        checkSaturday = findViewById(R.id.checkSaturday);
        checkSunday = findViewById(R.id.checkSunday);
        btnConfirmRepeat = findViewById(R.id.btnConfirmRepeat);

        // 뒤로가기 버튼 클릭 시
        btnB.setOnClickListener(v -> onBackPressed());

        // 알람 시간 설정 화면으로 이동
        alarmText.setOnClickListener(v -> {
            Intent intent = new Intent(AlramAdd.this, AlramSet.class);
            startActivityForResult(intent, REQUEST_CODE_SET_TIME);
        });

        // 시간 반복 설정용 NumberPicker 설정
        timeRepeatPicker.setMinValue(0);
        timeRepeatPicker.setMaxValue(23);
        minuteRepeatPicker.setMinValue(0);
        minuteRepeatPicker.setMaxValue(59);

        // 반복 설정 RadioGroup 리스너 설정
        repeatGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.hourlyRepeat) {
                timeRepeatContainer.setVisibility(View.VISIBLE);
                weeklyRepeatContainer.setVisibility(View.GONE);
                btnConfirmRepeat.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.weeklyRepeat) {
                timeRepeatContainer.setVisibility(View.GONE);
                weeklyRepeatContainer.setVisibility(View.VISIBLE);
                btnConfirmRepeat.setVisibility(View.VISIBLE);
            } else {
                timeRepeatContainer.setVisibility(View.GONE);
                weeklyRepeatContainer.setVisibility(View.GONE);
                btnConfirmRepeat.setVisibility(View.GONE);
            }
        });

        // 반복 설정을 확인하는 버튼
        btnConfirmRepeat.setOnClickListener(v -> {
            String repeatDetails = "반복 없음";
            if (hourlyRepeat.isChecked()) {
                int hours = timeRepeatPicker.getValue();
                int minutes = minuteRepeatPicker.getValue();
                if (hours == 0 && minutes == 0) {
                    Toast.makeText(this, "시간 또는 분을 설정해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                repeatDetails = hours + " 시간 " + minutes + " 분마다";
            } else if (weeklyRepeat.isChecked()) {
                StringBuilder days = new StringBuilder();
                if (checkMonday.isChecked()) days.append("월 ");
                if (checkTuesday.isChecked()) days.append("화 ");
                if (checkWednesday.isChecked()) days.append("수 ");
                if (checkThursday.isChecked()) days.append("목 ");
                if (checkFriday.isChecked()) days.append("금 ");
                if (checkSaturday.isChecked()) days.append("토 ");
                if (checkSunday.isChecked()) days.append("일 ");
                repeatDetails = "요일: " + days.toString().trim();

                if (days.length() == 0) {
                    Toast.makeText(this, "최소 하나의 요일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // 설정된 반복 정보 표시
            Toast.makeText(this, "선택된 반복 설정: " + repeatDetails, Toast.LENGTH_SHORT).show();
        });

        // 수정 작업인지 확인
        Intent intent = getIntent();
        String alarmData = intent.getStringExtra("alarmData");
        if (alarmData != null) {
            alarmText.setText(alarmData.split(" / ")[0]); // 알람 시간 설정
            // 반복 설정 초기화 코드 추가 가능
        }



        // 저장 버튼 클릭 시
        btnSave.setOnClickListener(v -> {
            String alarmTime = alarmText.getText().toString();
            if ("설정된 값 없음".equals(alarmTime)) {
                Toast.makeText(this, "알람 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                return;

            }

            String repeatDetails = "반복 없음";
            if (hourlyRepeat.isChecked()) {
                int hours = timeRepeatPicker.getValue();
                int minutes = minuteRepeatPicker.getValue();
                repeatDetails = hours + " 시간 " + minutes + " 분마다";
            } else if (weeklyRepeat.isChecked()) {
                StringBuilder days = new StringBuilder();
                if (checkMonday.isChecked()) days.append("월 ");
                if (checkTuesday.isChecked()) days.append("화 ");
                if (checkWednesday.isChecked()) days.append("수 ");
                if (checkThursday.isChecked()) days.append("목 ");
                if (checkFriday.isChecked()) days.append("금 ");
                if (checkSaturday.isChecked()) days.append("토 ");
                if (checkSunday.isChecked()) days.append("일 ");
                repeatDetails = "요일: " + days.toString().trim();

                if (days.length() == 0) {
                    Toast.makeText(this, "최소 하나의 요일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("alarmData", alarmTime + " / " + repeatDetails);
            if (intent.hasExtra("editPosition")) {
                resultIntent.putExtra("editPosition", intent.getIntExtra("editPosition", -1));
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // 알람 시간 설정 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SET_TIME && resultCode == RESULT_OK) {
            String selectedTime = data.getStringExtra("selectedTime");
            if (selectedTime != null) {
                alarmText.setText(selectedTime); // 알람 시간 업데이트
            }
        }
    }

    // 알람 시간과 반복 정보 저장
    private void saveAlarmTime(String alarmTime, String repeatDetails) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALARM_TIME, alarmTime);
        editor.putString(KEY_REPEAT_DETAILS, repeatDetails);
        editor.apply();
    }
}
