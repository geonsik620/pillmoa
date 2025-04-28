package com.example.capston_project_text1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class alarm_setting extends AppCompatActivity {
    private TextView alarmDurationValue; // 알람 지속 시간 표시 텍스트
    private TextView snoozeDurationValue; // 스누즈 지속 시간 표시 텍스트

    // SharedPreferences 키 정의
    private static final String PREF_NAME = "AlarmSettingsPreferences";
    private static final String KEY_ALARM_DURATION = "alarmDuration";
    private static final String KEY_SNOOZE_DURATION = "snoozeDuration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_alarm_setting);

        // 상단 뒤로가기 버튼
        ImageView backButton = findViewById(R.id.backButton);
        ImageView btnH = findViewById(R.id.btnH);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 현재 화면을 종료하여 이전 화면으로 돌아감
            }
        });

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(alarm_setting.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });


        // XML에서 텍스트뷰 연결
        alarmDurationValue = findViewById(R.id.alarm_duration_value);
        snoozeDurationValue = findViewById(R.id.snooze_duration_value);

        // SharedPreferences에서 기존에 저장된 값 복원
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedAlarmDuration = preferences.getString(KEY_ALARM_DURATION, "5분"); // 기본값: 5분
        String savedSnoozeDuration = preferences.getString(KEY_SNOOZE_DURATION, "5분"); // 기본값: 5분

        // 복원된 값으로 UI 업데이트
        alarmDurationValue.setText(savedAlarmDuration);
        snoozeDurationValue.setText(savedSnoozeDuration);

        // 알람 지속 시간 설정 레이아웃 클릭 이벤트 설정
        findViewById(R.id.alarm_duration_layout).setOnClickListener(v ->
                showAlarmDurationPicker()
        );

        // 스누즈 지속 시간 설정 레이아웃 클릭 이벤트 설정
        findViewById(R.id.snooze_duration_layout).setOnClickListener(v ->
                showSnoozeDurationPicker()
        );
    }

    /**
     * 알람 지속 시간 선택 다이얼로그를 표시하는 메서드
     */
    private void showAlarmDurationPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        // 알람 설정 레이아웃 불러오기
        View dialogView = inflater.inflate(R.layout.dialog_alarm_setting, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // 다이얼로그 내부 라디오 그룹 참조
        RadioGroup radioGroup = dialogView.findViewById(R.id.duration_radio_group);

        // 취소 버튼 클릭 이벤트 설정
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        // 확인 버튼 클릭 이벤트 설정
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId(); // 선택된 라디오 버튼 ID 가져오기
            if (selectedId != -1) {
                RadioButton selectedButton = dialogView.findViewById(selectedId); // 선택된 라디오 버튼 참조
                String selectedValue = selectedButton.getText().toString();

                // SharedPreferences에 저장
                SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_ALARM_DURATION, selectedValue);
                editor.apply();

                // 알람 지속 시간 업데이트
                alarmDurationValue.setText(selectedValue);
                dialog.dismiss(); // 다이얼로그 닫기
            } else {
                // 선택되지 않은 경우 경고 메시지 표시
                Toast.makeText(this, "알람 시간을 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // 다이얼로그 표시
    }

    /**
     * 스누즈 지속 시간 선택 다이얼로그를 표시하는 메서드
     */
    private void showSnoozeDurationPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        // 스누즈 설정 레이아웃 불러오기
        View dialogView = inflater.inflate(R.layout.dialog_alarm_setting_two, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // 다이얼로그 내부 라디오 그룹 참조
        RadioGroup radioGroup = dialogView.findViewById(R.id.duration_radio_group);

        // 취소 버튼 클릭 이벤트 설정
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        // 확인 버튼 클릭 이벤트 설정
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId(); // 선택된 라디오 버튼 ID 가져오기
            if (selectedId != -1) {
                RadioButton selectedButton = dialogView.findViewById(selectedId); // 선택된 라디오 버튼 참조
                String selectedValue = selectedButton.getText().toString();

                // SharedPreferences에 저장
                SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_SNOOZE_DURATION, selectedValue);
                editor.apply();

                // 스누즈 지속 시간 업데이트
                snoozeDurationValue.setText(selectedValue);
                dialog.dismiss(); // 다이얼로그 닫기
            } else {
                // 선택되지 않은 경우 경고 메시지 표시
                Toast.makeText(this, "스누즈 시간을 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // 다이얼로그 표시
    }
}