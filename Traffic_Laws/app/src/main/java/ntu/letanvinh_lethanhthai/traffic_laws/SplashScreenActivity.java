package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 3000; // Thời gian hiển thị splash screen (milliseconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Sử dụng Handler để trì hoãn việc chuyển sang MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để khởi chạy MainActivity
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Đóng SplashScreenActivity để người dùng không thể quay lại
                finish();
            }
        }, SPLASH_DELAY);
    }
}