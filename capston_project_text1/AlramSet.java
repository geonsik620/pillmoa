package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class AlramSet extends AppCompatActivity {
    NumberPicker hourPicker, minutePicker;
    Button btnSetTime, btnCancelTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alram_set);

        // Find Views
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);
        btnSetTime = findViewById(R.id.btnSetTime);
        btnCancelTime = findViewById(R.id.btnCancelTime);

        // Initialize NumberPickers
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        // "시간 설정" 버튼 클릭 시
        btnSetTime.setOnClickListener(v -> {
            int hour = hourPicker.getValue();
            int minute = minutePicker.getValue();
            String formattedTime = String.format("%02d:%02d", hour, minute);

            // 결과 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedTime", formattedTime);
            setResult(RESULT_OK, resultIntent);
            finish(); // 현재 액티비티 종료
        });

        // "시간 설정 취소" 버튼 클릭 시
        btnCancelTime.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish(); // 현재 액티비티 종료
        });
    }
}
