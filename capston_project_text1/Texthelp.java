package com.example.capston_project_text1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Texthelp extends AppCompatActivity {

    // View 변수
    private TextView titleTextView, bodyTextView, navTitleTextView;
    private Button prevButton, nextButton, playButton, rewindButton, forwardButton, volumeDownButton, volumeUpButton;
    private ImageView backButton, btnH;

    // MediaPlayer 및 관련 변수
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false; // 재생 상태 확인
    private float currentVolume = 1.0f; // 초기 볼륨

    // 현재 페이지를 추적할 변수
    private int currentPage = 0;

    // 페이지 데이터 (제목, 본문, 음성 파일 리소스 ID)
    private final Object[][] pages = {
            {"필모아 소개", "안녕하세요. \n우리는 살아가면서 의약품을 자주 접하게 됩니다.\n\n특히 요즘에는 편의점에서도 상비약을 판매할 만큼 의약품에 대한 접근성은 전에 비해 좋아진 것이 사실입니다.\n\n그런데 이렇게 쉽게 접하는 의약품일수록 부작용도 심해지고 있다는 사실을 알고 계시나요?\n\n의약품에는 복용 방법이 정해져 있고 주의사항들이 설명서로 제공되고 있긴 하지만 대부분 사람들은 TV 광고만 보고 복용하고는 합니다.\n\n안타깝게도 식품의약품안전처에 따르면 이렇게 의약품을 아무런 주의 없이 복용하면서 나타나는 부작용이 매년 증가하고 있다고 합니다.\n\n저희 프로젝트 3조는 이러한 의약품 오남용을 막기 위해 모두 손쉽게 활용할 수 있는 의약품 정보 제공 애플리케이션 필모아를 개발하고 배포하게 되었습니다.\n\n이 애플리케이션을 통해 모두가 올바른 의약품 사용으로 부작용 없는 건강한 삶을 영위하기를 희망합니다.\n\n감사합니다.", "R.raw.introduction"},
            {"의약품 정보 검색 방법", "의약품 정보 검색 방법입니다. \n필모아에서 의약품을 검색하는 방법은 휴대폰 카메라를 이용해 의약품에 인쇄되어 있는 바코드나 QR 코드를 촬영하여 인식시키는 방법이 있습니다.\n참고로 판독 가능한 의약품의 범위는 국내에서 정식으로 판매되는 의약품이며, 건강보조식품이나 일반 식품은 검색되지 않습니다.\n\n1. 바코드 및 QR 코드 검색\n\n우선 휴대폰 카메라를 이용해 바코드와 QR 코드를 인식하는 방법을 안내해 드리겠습니다.\n필모아 초기 메뉴에서 의약품 정보 검색을 선택합니다. 그리고 바코드, QR 코드 검색을 선택합니다.\n이때 카메라 접근 승인 여부에 대한 도움말이 나타나며, 다음부터 표시하지 않도록 하려면 다시 보지 않기를 눌러주시거나 촬영을 취해 확인을 눌러주세요.\n이후 휴대폰 카메라가 켜지면서 촬영이 시작됩니다. 휴대폰이 소리 모드로 되어 있는 경우 뚜 뚜 소리가 나면서 현재 인식을 시도하는 중임을 알 수 있습니다.\n정확한 인식을 위해 휴대폰 카메라를 의약품의 바코드와 QR 코드로 붙어 한 뼘 정도의 거리를 두고 촬영해 보세요.\n인식이 잘 되지 않으면 휴대폰을 상하좌우로 이동하며 바코드를 인식해 보세요.\n참고로 제품 유효기간 정보는 일반적으로 제조일을 기준으로 하고 있으므로 의약품 구입 시 유통기간을 확인하는 것이 좋습니다.\n또한 의약품의 보관 방법과 개봉 후 상태에 따라 유통기한이 현저히 줄어들 수 있으니 주의하여 주시기 바랍니다.\n\n2. 텍스트 검색\n\n텍스트 검색은 사용자가 직접 키보드로 의약품 이름을 입력하여 검색하는 방식입니다.\n의약품명 전체 또는 부분 검색이 가능합니다. 예를 들어 게보린정을 검색하고자 할 때 개보링까지만 입력하셔도 관련 목록이 나타납니다.\n한 글자로 된 의약품의 경우 다소 검색 시간이 소요될 수 있으니 이 점 이용에 참고해 주시기 바랍니다.\n검색되어 나온 목록 중에서 원하는 의약품 이름을 선택하시면 해당 의약품에 대한 상세 내용을 열람하실 수 있습니다.", "R.raw.search_guide"},
            {"처방전 열람 방법", "처방전 열람 방법입니다.\n병원에서 진료를 받은 후에는 대부분 필요한 의약품을 처방받게 됩니다.\n일반적으로 처방전은 2장을 받게 되는데요, 한 장은 약국 제출용이고 나머지 한 장은 환자 보관용입니다.\n만약 병원에서 처방전을 한 장만 주는 경우, 환자 보관용도 요청할 수 있습니다.\n처방전에는 병원 이름과 환자 정보 등 기본 정보와 함께 의약품 목록이 출력되어 있습니다.\n최근 대부분 처방전에는 QR 코드가 인쇄되어 있습니다.\n필모아에서는 국내 대부분에서 발급하는 처방전의 QR 코드를 판독할 수 있습니다.\n그러나 2019년 현재에는 처방전의 QR 코드가 표준화되어 있지 않아 일부 처방전의 QR 코드는 인식되지 않을 수 있습니다.", "R.raw.prescription_view"},
            {"의약품 및 처방전 검색 이력 이용 방법", "1. 의약품 검색 이력\n\n의약품 검색 이력에서 의약품 또는 처방전을 열람했던 기록을 제공받을 수 있습니다.\n이곳에는 최근 20개까지 검색한 이력이 저장됩니다. 초기 메뉴에서 의약품 검색 이력을 선택하시면 최근에 검색했던 의약품 목록을 확인할 수 있습니다.\n목록 중에서 원하는 의약품을 선택하면 기본 정보 등 의약품의 세부 정보 항목을 열람하실 수 있습니다.\n\n2. 처방전 검색 이력\n\n처방전 검색 이력에는 QR 코드를 통해 열람했던 처방전들이 최근 20개까지 저장되고 재열람할 수 있습니다.\n처방전 열람 이력에는 의료기관명, 의료인 등 기본 정보가 표시되며, 원하시는 처방전을 선택하시면 기본 정보 및 의약품 목록을 열람하실 수 있습니다.", "R.raw.search_history_usage"},
            {"환경 설정 이용 방법", "1. 개인정보 설정\n\n개인정보 설정은 필모아를 사용하는 사용자들의 기본 정보를 설정하는 곳입니다.\n이 설정에서는 시각장애 여부, 성별, 여성인 경우 임신 여부를 설정할 수 있습니다.\n필모아의 모든 설정 내용은 통계 목적으로만 사용되며 다른 목적으로는 사용되지 않습니다.\n\n2. 앱 환경정보 설정\n\n앱 환경정보 설정에서는 앱 잠금 기능과 무선 데이터 사용 설정을 관리할 수 있습니다.\n앱 잠금 기능을 사용하면 앱 실행 시 비밀번호 또는 지문을 통해 보안을 설정할 수 있습니다.\n지문 잠금의 경우, 휴대폰 설정에 등록된 지문을 그대로 사용할 수 있습니다.\n\n무선 데이터 사용 설정은 필모아가 와이파이 또는 무선 인터넷이 연결된 환경에서 작동할 수 있도록 관리합니다.\n필요에 따라 무선 데이터 사용 여부를 설정해 주세요. 필모아는 일반적으로 많은 데이터를 사용하지 않지만, 사용자 환경에 따라 조정할 수 있습니다.\n\n푸시 알림 설정은 필모아에서 특별한 알림이 있을 때 이를 수신할지 여부를 결정할 수 있는 메뉴입니다. 스마트폰의 알림 기능과 연동되어 문자 메시지 외에도 앱별 알림을 관리할 수 있습니다.", "R.raw.settings_usage"},
            {"기타 문의", "필모아에 대해 더 궁금한 점이 있으신 분은 저희 3조에 따로 문의 주시기 바랍니다.", "R.raw.additional_inquiries"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texthelp_file);

        // Intent로 전달된 페이지 번호 받기
        currentPage = getIntent().getIntExtra("page", 0);
        Log.d("TextHelp", "Page received: " + currentPage);

        // View 연결
        initializeViews();

        // 페이지 정보 설정
        setPageInfo(currentPage);

        // 버튼 클릭 이벤트 설정
        setButtonListeners();
    }

    private void initializeViews() {
        titleTextView = findViewById(R.id.titleTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        playButton = findViewById(R.id.playButton);
        rewindButton = findViewById(R.id.rewindButton);
        forwardButton = findViewById(R.id.forwardButton);
        volumeDownButton = findViewById(R.id.volumeDownButton);
        volumeUpButton = findViewById(R.id.volumeUpButton);
        backButton = findViewById(R.id.backButton);
        btnH = findViewById(R.id.btnH); // 홈 버튼
    }

    private void setButtonListeners() {
        prevButton.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                setPageInfo(currentPage);
            } else {
                Toast.makeText(this, "첫 페이지입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentPage < pages.length - 1) {
                currentPage++;
                setPageInfo(currentPage);
            } else {
                Toast.makeText(this, "마지막 페이지입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        playButton.setOnClickListener(v -> {
            if (isPlaying) {
                stopAudio();
            } else {
                playAudio(pages[currentPage][2].toString());
            }
        });

        volumeDownButton.setOnClickListener(v -> adjustVolume(-0.1f));
        volumeUpButton.setOnClickListener(v -> adjustVolume(0.1f));
        rewindButton.setOnClickListener(v -> rewindAudio());
        forwardButton.setOnClickListener(v -> forwardAudio());

        // 뒤로가기 버튼 클릭 시 이전 화면으로 돌아가기
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // 현재 Activity 종료
            }
        });

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(Texthelp.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택 정리
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });

    }

    private void setPageInfo(int pageIndex) {
        titleTextView.setText((String) pages[pageIndex][0]);
        bodyTextView.setText((String) pages[pageIndex][1]);
        stopAudio(); // 페이지 전환 시 오디오 중단
    }

    private void playAudio(String audioFileName) {
        if (isPlaying) {
            Toast.makeText(this, "이미 재생 중입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        releaseMediaPlayer();

        // 리소스 이름에서 "R.raw." 접두사 제거
        String resourceName = audioFileName.replace("R.raw.", "");

        // 리소스 ID 가져오기
        int audioResId = getResources().getIdentifier(resourceName, "raw", getPackageName());

        if (audioResId != 0) {
            mediaPlayer = MediaPlayer.create(this, audioResId);
            mediaPlayer.start();
            isPlaying = true;
            mediaPlayer.setOnCompletionListener(mp -> stopAudio());
        } else {
            Toast.makeText(this, "오디오 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        releaseMediaPlayer();
    }

    private void adjustVolume(float delta) {
        if (mediaPlayer != null) {
            currentVolume = Math.max(0.0f, Math.min(currentVolume + delta, 1.0f)); // 0.0 ~ 1.0 사이로 제한
            mediaPlayer.setVolume(currentVolume, currentVolume);
        }
    }

    private void rewindAudio() {
        if (mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(Math.max(currentPosition - 5000, 0));
        }
    }

    private void forwardAudio() {
        if (mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(Math.min(currentPosition + 5000, mediaPlayer.getDuration()));
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAudio(); // 앱 일시 중지 시 오디오 정지
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer(); // 앱 종료 시 MediaPlayer 해제
    }
}