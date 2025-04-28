package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Basic extends AppCompatActivity {
    private TextView infoTextView;
    ImageView btnB, btnH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);

        // 상단 뒤로가기 버튼
        btnB = findViewById(R.id.btnB);
        btnH = findViewById(R.id.btnH);

        btnB.setOnClickListener(v -> onBackPressed()); // 뒤로가기 기능

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Basic.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

        String itemName = getIntent().getStringExtra("itemName");

        infoTextView = findViewById(R.id.btxt);
        if (itemName != null) {
            fetchDrugDetails(itemName);
        } else {
            infoTextView.setText("의약품 정보를 불러올 수 없습니다.");
        }
    }




    private void fetchDrugDetails(String itemName) {
        new Thread(() -> {
            try {
                // API URL 구성
                String apiUrl = "https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"
                        + "?serviceKey=VYUdjQTqFAYSbaGN9CpzMPCAY5KMUpy0TZLu98vIp5OGYw%2F6s3yCnDxwt%2BBmeou8dcTbO1eZLkPIxgUY7oxvFA%3D%3D"
                        + "&itemName=" + URLEncoder.encode(itemName, "UTF-8")
                        + "&pageNo=1"
                        + "&numOfRows=1"
                        + "&type=xml";

                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    parseXmlResponse(response.toString());
                } else {
                    runOnUiThread(() -> infoTextView.setText("API 호출 실패: 응답 코드 " + responseCode));
                }
            } catch (Exception e) {
                runOnUiThread(() -> infoTextView.setText("에러 발생: " + e.getMessage()));
                e.printStackTrace();
            }
        }).start();
    }

    private void parseXmlResponse(String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(response));

            String tag = null;
            String itemName = null, entpName = null, efcyQesitm = null, useMethodQesitm = null;
            String atpnQesitm = null, atpnWarnQesitm = null, intrcQesitm = null;
            String seQesitm = null, depositMethodQesitm = null;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if (tag != null) {
                            switch (tag) {
                                case "itemName":
                                    itemName = parser.getText();
                                    break;
                                case "entpName":
                                    entpName = parser.getText();
                                    break;
                                case "efcyQesitm":
                                    efcyQesitm = parser.getText();
                                    break;
                                case "useMethodQesitm":
                                    useMethodQesitm = parser.getText();
                                    break;
                                case "atpnQesitm":
                                    atpnQesitm = parser.getText();
                                    break;
                                case "atpnWarnQesitm":
                                    atpnWarnQesitm = parser.getText();
                                    break;
                                case "intrcQesitm":
                                    intrcQesitm = parser.getText();
                                    break;
                                case "seQesitm":
                                    seQesitm = parser.getText();
                                    break;
                                case "depositMethodQesitm":
                                    depositMethodQesitm = parser.getText();
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            // 모든 정보를 TextView에 표시
                            StringBuilder displayInfo = new StringBuilder();
                            displayInfo.append("제품명: ").append(itemName != null ? itemName : "정보 없음").append("\n\n");
                            displayInfo.append("업체명: ").append(entpName != null ? entpName : "정보 없음").append("\n\n");
                            displayInfo.append("효능: ").append(efcyQesitm != null ? efcyQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("사용법: ").append(useMethodQesitm != null ? useMethodQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("주의사항: ").append(atpnQesitm != null ? atpnQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("경고사항: ").append(atpnWarnQesitm != null ? atpnWarnQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("상호작용: ").append(intrcQesitm != null ? intrcQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("부작용: ").append(seQesitm != null ? seQesitm : "정보 없음").append("\n\n");
                            displayInfo.append("보관법: ").append(depositMethodQesitm != null ? depositMethodQesitm : "정보 없음");

                            runOnUiThread(() -> infoTextView.setText(displayInfo.toString()));
                        }
                        tag = null;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            runOnUiThread(() -> infoTextView.setText("데이터 처리 중 오류 발생: " + e.getMessage()));
            e.printStackTrace();
        }
        ImageView btnB = findViewById(R.id.btnB);
        btnB.setOnClickListener(v -> {
            finish(); // 현재 액티비티를 종료하여 이전 액티비티 상태 유지
        });
        ImageView btnH = findViewById(R.id.btnH);
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Basic.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
