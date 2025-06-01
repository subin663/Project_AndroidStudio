package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView; // Ký tự này không được sử dụng nhưng bạn có thể giữ nó nếu muốn sử dụng sau này
import android.widget.TextView;
import android.widget.Toast; // Ký tự này không được sử dụng nhưng bạn có thể giữ nó nếu muốn sử dụng sau này

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale; // Import Locale để sử dụng String.format

public class Answer_Activity extends AppCompatActivity {

    TextView tvAns, tvDanhgia;
    Button btnBack; // Khai báo nhưng chưa sử dụng, bạn có thể thêm OnClickListener cho nó

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
//        btnBack = findViewById(R.id.btnBack); // Đảm bảo bạn có một Button với ID này trong layout

        // Nhận dữ liệu từ Intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        // Nhận trạng thái lỗi câu hỏi liệt
        boolean hasCriticalError = getIntent().getBooleanExtra("hasCriticalError", false);

        // Số câu đúng tối thiểu để đậu
        int passingThreshold = 21; // Ví dụ: 21/25 câu đúng

        // Tạo thông báo đánh giá
        String messageAns, messageDanhgia;

        if (hasCriticalError) {
            // Nếu có lỗi câu hỏi liệt, tự động KHÔNG ĐẠT
            messageAns = "KHÔNG ĐẠT!";
            messageDanhgia = "Bạn đã trả lời sai câu hỏi điểm liệt, vì vậy bạn KHÔNG ĐẠT bài kiểm tra.";
        } else if (correctAnswers >= passingThreshold) {
            // Nếu không có lỗi câu hỏi liệt và đủ số câu đúng
            messageAns = "CHÚC MỪNG!";
            messageDanhgia = String.format(Locale.getDefault(), "Bạn đã ĐẠT bài kiểm tra với %d/%d câu đúng.", correctAnswers, totalQuestions);
        } else {
            // Không có lỗi câu hỏi liệt nhưng không đủ số câu đúng
            messageAns = "CHƯA ĐẠT!";
            messageDanhgia = String.format(Locale.getDefault(), "Bạn đã trả lời đúng %d/%d câu. Bạn cần %d/%d câu đúng để ĐẠT.", correctAnswers, totalQuestions, passingThreshold, totalQuestions);
        }

        tvAns.setText(messageAns);
        tvDanhgia.setText(messageDanhgia);

        // Xử lý sự kiện nút Back (nếu bạn muốn quay lại màn hình trước đó)
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Kết thúc Activity hiện tại để quay lại Activity trước đó
                }
            });
        }
    }
}