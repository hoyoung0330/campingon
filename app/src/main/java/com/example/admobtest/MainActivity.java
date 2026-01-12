package com.example.admobtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private static final String TAG = "AdMobDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 초기화 및 로드 코드 점검: MobileAds.initialize 확실하게 호출
        // 메인 스레드에서 초기화합니다.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(TAG, "AdMob SDK Initialized");
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        // 3. 디버깅을 위한 AdListener 추가 (가장 중요)
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d(TAG, "Ad Clicked");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "Ad Closed");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                // Code to be executed when an ad request fails.
                // 3. 실패 시 LoadAdError 객체의 에러 코드와 메시지 로그 출력
                String errorDomain = loadAdError.getDomain();
                int errorCode = loadAdError.getCode();
                String errorMessage = loadAdError.getMessage();
                String responseInfo = loadAdError.getResponseInfo() != null ? loadAdError.getResponseInfo().toString() : "null";

                Log.e(TAG, String.format("Ad Failed to Load. Domain: %s, Code: %d, Message: %s", errorDomain, errorCode, errorMessage));
                Log.e(TAG, "Response Info: " + responseInfo);

                // 에러 코드 해석 힌트
                switch (errorCode) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        Log.e(TAG, "Error Code: INTERNAL_ERROR (0) - Something happened internally; for instance, an invalid response was received from the ad server.");
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        Log.e(TAG, "Error Code: INVALID_REQUEST (1) - The ad request was invalid; for instance, the ad unit ID was incorrect.");
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        Log.e(TAG, "Error Code: NETWORK_ERROR (2) - The ad request was unsuccessful due to network connectivity.");
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        Log.e(TAG, "Error Code: NO_FILL (3) - The ad request was successful, but no ad was returned due to lack of ad inventory.");
                        break;
                }

                // UI에 에러 메시지 표시 (운영 환경 테스트 시 유용)
                String toastMessage = String.format("Ad Failed: %d (%s)", errorCode, errorMessage);
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.d(TAG, "Ad Impression recorded");
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "Ad Loaded Successfully");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "Ad Opened");
            }
        });

        // 2. 초기화 및 로드 코드 점검: adView.loadAd 함수가 실제로 실행되는지 확인
        Log.d(TAG, "Requesting Ad...");
        mAdView.loadAd(adRequest);
    }
}
