package ntu.letanvinh_lethanhthai.traffic_laws;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnswerDeLiet_Acivity extends AppCompatActivity {

    TextView tvAns, tvDanhgia;
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
        // Tìm điều khiển
        // Ánh xạ các TextView
        tvAns = findViewById(R.id.tvAns);
        tvDanhgia = findViewById(R.id.tvEvaluate);

        // Nhận dữ liệu từ Intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Tính toán số câu sai
        int incorrectAnswers = totalQuestions - correctAnswers;

        // Tính toán đậu hay rớt (đúng 25/25 câu)
        boolean isPassed = correctAnswers == 25 && totalQuestions == 25;

        // Tạo thông báo đánh giá và hiển thị lên TextView
        String messageAns, messageDanhgia;
        if (isPassed) {
            messageAns = "CONGRATULATIONS";
            messageDanhgia = "Bạn đã đậu bài kiểm tra với " + correctAnswers + "/" + totalQuestions + " câu đúng.";
        } else {
            messageAns = "BETTER LUCK NEXT TIME!";
            messageDanhgia = "Bạn đã trả lời đúng " + correctAnswers + "/" + totalQuestions + " câu. Bạn cần 25/25 câu để đậu.";
        }
        // Hiển thị nội dung cho textView
        tvAns.setText(messageAns);
        tvDanhgia.setText(messageDanhgia);
    }
}
