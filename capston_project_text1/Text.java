package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Text extends AppCompatActivity {
    ListView listView;
    ArrayList<String> drugList; // 의약품 목록
    ArrayList<String> drugDetails; // 상세 정보 저장
    ImageView btnB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        // 뷰 초기화
        listView = findViewById(R.id.txt);
        btnB = findViewById(R.id.btnB);

        drugList = new ArrayList<>();
        drugDetails = new ArrayList<>();

        // 검색어 가져오기
        String query = getIntent().getStringExtra("query");

        if (query != null && !query.isEmpty()) {
            fetchAndDisplayDrugs(query);
        }

        // 리스트 아이템 클릭 이벤트
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Text.this, Infor.class);
            intent.putExtra("itemName", drugList.get(position));
            intent.putExtra("itemDetails", drugDetails.get(position));
            startActivity(intent);
        });

        // 뒤로가기 버튼
        ImageView btnB = findViewById(R.id.btnB);
        btnB.setOnClickListener(v -> {
            finish(); // 현재 액티비티를 종료하여 이전 액티비티 상태 유지
        });
        ImageView btnH = findViewById(R.id.btnH);
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Text.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }


    private void fetchAndDisplayDrugs(String query) {
        new Thread(() -> {
            String apiResponse = fetchApiData(query);
            runOnUiThread(() -> {
                if (apiResponse != null) {
                    parseXmlResponse(apiResponse);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, drugList);
                    listView.setAdapter(adapter);
                } else {
                    drugList.add("검색 결과를 가져오는 데 실패했습니다.");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, drugList);
                    listView.setAdapter(adapter);
                }
            });
        }).start();
    }

    // API 요청
    private String fetchApiData(String drugName) {
        try {
            String apiUrl = "https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"
                    + "?serviceKey=VYUdjQTqFAYSbaGN9CpzMPCAY5KMUpy0TZLu98vIp5OGYw%2F6s3yCnDxwt%2BBmeou8dcTbO1eZLkPIxgUY7oxvFA%3D%3D"
                    + "&itemName=" + URLEncoder.encode(drugName, "UTF-8")
                    + "&pageNo=1"
                    + "&numOfRows=30"
                    + "&type=xml";

            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // XML 데이터 파싱
    private void parseXmlResponse(String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(response));

            String tag = null;
            String itemName = null, entpName = null;

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
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            if (itemName != null && entpName != null) {
                                drugList.add(itemName);
                                drugDetails.add("제품명: " + itemName + "\n업체명: " + entpName);
                            }
                            itemName = null;
                            entpName = null;
                        }
                        tag = null;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
