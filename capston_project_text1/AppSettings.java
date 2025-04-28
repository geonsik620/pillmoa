package com.example.capston_project_text1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppSettings extends AppCompatActivity {

    private ImageView appLockOptionImage;
    private ImageView dataUsageOptionImage;
    private ImageView pushNotificationOptionImage;

    // 각 옵션의 선택 상태를 저장할 변수
    private boolean isAppLockSelected = false;
    private boolean isDataUsageSelected = false;
    private boolean isPushNotificationSelected = false;

    // SharedPreferences 키 정의
    private static final String PREF_NAME = "AppSettingsPreferences";
    private static final String KEY_APP_LOCK = "appLock";
    private static final String KEY_DATA_USAGE = "dataUsage";
    private static final String KEY_PUSH_NOTIFICATION = "pushNotification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appsettings);

        // LinearLayout 참조 및 클릭 리스너 설정
        LinearLayout alarmBaseSet = findViewById(R.id.alarm_base_set);
        alarmBaseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlarmSettingsActivity로 이동
                Intent intent = new Intent(AppSettings.this, alarm_setting.class);
                startActivity(intent);
            }
        });

        // SharedPreferences 초기화 및 상태 복원
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        isAppLockSelected = preferences.getBoolean(KEY_APP_LOCK, false);
        isDataUsageSelected = preferences.getBoolean(KEY_DATA_USAGE, false);
        isPushNotificationSelected = preferences.getBoolean(KEY_PUSH_NOTIFICATION, false);

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
            Intent intent = new Intent(AppSettings.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });


        // 각 옵션의 LinearLayout 참조
        LinearLayout appLockOption = findViewById(R.id.appLockOptionLayout);
        LinearLayout dataUsageOption = findViewById(R.id.dataUsageOptionLayout);
        LinearLayout pushNotificationOption = findViewById(R.id.pushNotificationOptionLayout);

        // 각 옵션의 ImageView 참조
        appLockOptionImage = findViewById(R.id.appLockOption);
        dataUsageOptionImage = findViewById(R.id.dataUsageOption);
        pushNotificationOptionImage = findViewById(R.id.pushNotificationOption);

        // 초기 상태 설정
        updateOptionImage(appLockOptionImage, isAppLockSelected);
        updateOptionImage(dataUsageOptionImage, isDataUsageSelected);
        updateOptionImage(pushNotificationOptionImage, isPushNotificationSelected);

        // 클릭 리스너 설정
        appLockOption.setOnClickListener(new OptionClickListener(KEY_APP_LOCK));
        dataUsageOption.setOnClickListener(new OptionClickListener(KEY_DATA_USAGE));
        pushNotificationOption.setOnClickListener(new OptionClickListener(KEY_PUSH_NOTIFICATION));
    }

    // 옵션 클릭 리스너 클래스
    private class OptionClickListener implements View.OnClickListener {
        private final String key;

        OptionClickListener(String key) {
            this.key = key;
        }

        @Override
        public void onClick(View view) {
            SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            // 상태 토글 및 저장
            if (key.equals(KEY_APP_LOCK)) {
                isAppLockSelected = !isAppLockSelected;
                editor.putBoolean(KEY_APP_LOCK, isAppLockSelected);
                updateOptionImage(appLockOptionImage, isAppLockSelected);
            } else if (key.equals(KEY_DATA_USAGE)) {
                isDataUsageSelected = !isDataUsageSelected;
                editor.putBoolean(KEY_DATA_USAGE, isDataUsageSelected);
                updateOptionImage(dataUsageOptionImage, isDataUsageSelected);

                // 무선 데이터가 해제되면 알림 창 띄우기
                if (!isDataUsageSelected) {
                    showDataUsageWarning();
                }
            } else if (key.equals(KEY_PUSH_NOTIFICATION)) {
                isPushNotificationSelected = !isPushNotificationSelected;
                editor.putBoolean(KEY_PUSH_NOTIFICATION, isPushNotificationSelected);
                updateOptionImage(pushNotificationOptionImage, isPushNotificationSelected);
            }

            // 변경 사항 저장
            editor.apply();
        }
    }

    // ImageView 상태 업데이트
    private void updateOptionImage(ImageView imageView, boolean isSelected) {
        imageView.setImageResource(isSelected ? R.drawable.ic_radio_checked : R.drawable.ic_radio_unchecked);
    }

    // 무선 데이터 사용에 대한 알림 창 띄우기
    private void showDataUsageWarning() {
        new AlertDialog.Builder(AppSettings.this)
                .setTitle("무선 데이터 사용")
                .setMessage("무선 데이터 사용을 미선택시에는 WIFI 환경에서만 서비스 이용이 가능합니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // "확인" 버튼을 눌렀을 때의 동작 (특별한 동작은 없음)
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // "취소" 버튼을 눌렀을 때의 동작
                        // 무선 데이터 옵션을 다시 선택 상태로 변경
                        isDataUsageSelected = true;
                        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(KEY_DATA_USAGE, isDataUsageSelected);
                        editor.apply();

                        // 데이터 사용 상태 갱신
                        updateOptionImage(dataUsageOptionImage, isDataUsageSelected);
                    }
                })
                .setCancelable(false)  // 사용자가 배경을 클릭하거나 뒤로가기 버튼을 눌러도 알림 창을 닫을 수 없게 설정
                .show();
    }
}