package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Answer_Activity extends AppCompatActivity {

    TextView tvAns, tvDanhgia;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvAns = findViewById(R.id.tvAns);
        tvDanhgia = findViewById(R.id.tvEvaluate);

        // Nhận dữ liệu từ Intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Số câu sai
        int incorrectAnswers = totalQuestions - correctAnswers;

        //Đậu hay rớt (đúng 21/25 câu)
        boolean isPassed = correctAnswers >= 21 && totalQuestions == 25;

        // Tạo thông báo đánh giá và hiển thị lên TextView
        String messageAns, messageDanhgia;
        if (isPassed) {
            messageAns = "CONGRATULATIONS";
            messageDanhgia = "Bạn đã đậu bài kiểm tra với " + correctAnswers + "/" + totalQuestions + " câu đúng.";
        } else {
            messageAns = "BETTER LUCK NEXT TIME!";
            messageDanhgia = "Bạn đã trả lời đúng " + correctAnswers + "/" + totalQuestions + " câu. Bạn cần 21/25 câu để đậu.";
        }

        tvAns.setText(messageAns);
        tvDanhgia.setText(messageDanhgia);


    }
}
