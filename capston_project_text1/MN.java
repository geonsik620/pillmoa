package com.example.capston_project_text1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MN extends AppCompatActivity {
    private static final String PREF_NAME = "AlarmPreferences";
    private static final String KEY_ALARM_LIST = "alarm_list";

    ImageView btnB, btnH;
    Button btnA, btnD;
    RecyclerView recyclerViewAlarms;
    AlramAdapter adapter;
    List<String> alarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn);

        btnB = findViewById(R.id.btnB);
        btnH = findViewById(R.id.btnH);
        btnA = findViewById(R.id.btnA);
        btnD = findViewById(R.id.btnD);
        recyclerViewAlarms = findViewById(R.id.recyclerViewAlarms);

        // Load saved alarms
        alarmList = loadAlarms();

        // Set up RecyclerView
        adapter = new AlramAdapter(alarmList, new AlramAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Handle edit action
                Intent intent = new Intent(MN.this, AlramAdd.class);
                intent.putExtra("editPosition", position);
                intent.putExtra("alarmData", alarmList.get(position));
                startActivityForResult(intent, 1); // 1: request code for edit
            }

            @Override
            public void onDeleteClick(int position) {
                // Handle delete action
                alarmList.remove(position);
                saveAlarms();
                adapter.notifyDataSetChanged();
            }
        });
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAlarms.setAdapter(adapter);

        // Back button
        btnB.setOnClickListener(v -> onBackPressed());

        // Home button
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(MN.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add alarm button
        btnA.setOnClickListener(v -> {
            Intent intent = new Intent(MN.this, AlramAdd.class);
            startActivityForResult(intent, 2); // 2: request code for add
        });

        // Delete all alarms button
        btnD.setOnClickListener(v -> {
            alarmList.clear();
            saveAlarms();
            adapter.notifyDataSetChanged();
        });
    }

    // Load alarms from SharedPreferences
    private List<String> loadAlarms() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> alarmSet = preferences.getStringSet(KEY_ALARM_LIST, new HashSet<>());
        return new ArrayList<>(alarmSet);
    }

    // Save alarms to SharedPreferences
    private void saveAlarms() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(KEY_ALARM_LIST, new HashSet<>(alarmList));
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String alarmData = data.getStringExtra("alarmData");
            if (alarmData != null) {
                if (requestCode == 1) { // Edit alarm
                    int position = data.getIntExtra("editPosition", -1);
                    if (position != -1) {
                        alarmList.set(position, alarmData); // 수정된 데이터 반영
                    }
                } else if (requestCode == 2) { // Add alarm
                    alarmList.add(alarmData); // 새로운 알람 추가
                }
                saveAlarms();
                adapter.notifyDataSetChanged();
            }
        }
    }


}
