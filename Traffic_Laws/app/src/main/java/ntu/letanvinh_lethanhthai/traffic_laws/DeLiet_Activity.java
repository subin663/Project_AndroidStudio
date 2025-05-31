package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer; // Thêm import này
import android.util.Log;
import android.widget.Button;
import android.widget.TextView; // Thêm import này
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity; // Đổi từ androidx.activity.EdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
// Xóa các import không cần thiết nếu bạn đã dùng AppCompatActivity thay vì EdgeToEdge
// import androidx.activity.EdgeToEdge;
// import androidx.core.graphics.Insets;
// import androidx.core.view.ViewCompat;
// import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // Thêm import này
import java.util.concurrent.TimeUnit; // Thêm import này

public class DeLiet_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    TextView timerTextView; // Khai báo TextView cho timer
    CountDownTimer countDownTimer; // Khai báo CountDownTimer
    private static final long QUIZ_DURATION_MILLIS = 1140000; // 5 phút (5 * 60 * 1000)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1); // Đảm bảo bạn đang sử dụng layout activity_de_liet.xml

            //Tìm điều khiển
           submitButton = findViewById(R.id.submitButton);
           timerTextView = findViewById(R.id.timer_text); // Khởi tạo timer TextView

        questionList = loadQuestionsFromAssets();

        if (questionList != null)
        {
            // Mảng chứa các câu trả lời của người dùng
            userAnswers = new ArrayList<>(questionList.size());
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null);
            }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                userAnswers.set(position, selectedAnswer); // thiết lập các vị trí tương ứng câu trả lời người dùng
                Log.d("DeLiet_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer);
            });
            recyclerView.setAdapter(adapter);

            startQuizTimer(); // Bắt đầu đếm giờ khi quiz được tải

        } else
        {
            Log.e("DeLiet_Activity", "Không thể load danh sách câu hỏi."); // Đổi tên Activity trong Log
            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }

        submitButton.setOnClickListener(v -> submitQuiz()); // Gán Listener cho nút nộp bài
    }

    // Bộ đếm thời gian làm bài
    private void startQuizTimer() {
        countDownTimer = new CountDownTimer(QUIZ_DURATION_MILLIS, 1000)  // tạo đồng hồ đếm ngược (millisecond)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                // Chuyển millisecond sang phút và giây
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes); // số giây còn lại bằng số giây - số phút trước đó
                // Dạng hiển thị của đồng hồ MM:SS 09:30
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(timeLeftFormatted); // Thiết lặp hiển thị
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                Toast.makeText(DeLiet_Activity.this, "Hết giờ! Tự động nộp bài.", Toast.LENGTH_LONG).show();
                submitQuiz(); // Tự động nộp bài khi hết giờ
            }
        }.start();
    }

    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng timer khi quiz được nộp
        }

        int correctAnswers = adapter.getCorrectAnswersCount(); // Lấy số câu đúng người dùng
        int totalQuestions = questionList.size(); // Số lượng câu hỏi

        // Thay đổi Intent để chuyển đến Answer_Activity (hoặc một Activity kết quả phù hợp)
        Intent intent = new Intent(this, AnswerDeLiet_Acivity.class);
        // Truyền theo data
        intent.putExtra("correctAnswers", correctAnswers); // Truyền số câu đúng
        intent.putExtra("totalQuestions", totalQuestions); // Truyền tổng số câu
        startActivity(intent);
        finish(); // Kết thúc Activity sau khi nộp bài
    }

    // Xử lý xoá sạch khi rời khỏi Activity (chuyển trang, thoát app)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Đảm bảo timer bị hủy để tránh rò rỉ bộ nhớ
        }
    }

    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {

            InputStream doc_file = getAssets().open("quiz_1.json"); // Ví dụ: "de_liet.json"
            int size = doc_file.available(); // số lượng câu hỏi trong file
            byte[] buffer = new byte[size]; // Tạo kho chứa lưu trữ câu hỏi
            int read = doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8"); // chứa nội dung file json theo kho buffer
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Chuyển json thành mảng, mỗi câu hỏi là 1 phần tử trong mảng

            // // Duyệt qua từng câu hỏi trong jsonArray lấy dữ liệu
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question");
                JSONArray opts = cau_hoi.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j));
                }
                String image = cau_hoi.getString("image");
                String answer = cau_hoi.getString("answer");
                questions.add(new All_Question(qText, options, answer, image)); // Thêm mới câu hỏi và thêm vào danh sách câu hỏi
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}