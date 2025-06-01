package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer; // Thêm import này
import android.util.Log;
import android.widget.Button;
import android.widget.TextView; // Thêm import này
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // Thêm import này
import java.util.concurrent.TimeUnit; // Thêm import này

public class Quiz2_Activity extends AppCompatActivity  {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    TextView timerTextView; // Khai báo TextView cho timer
    CountDownTimer countDownTimer; // Khai báo CountDownTimer
    static final long QUIZ_DURATION_MILLIS = 1140000; // 19 phút (19 * 60 * 1000)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1); //Sử dụng layout có timer_text

        // Tìm điều khiển
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timer_text); // Khởi tạo timer TextView
        recyclerView = findViewById(R.id.recyclerView);


        // Lấy dữ liệu câu hỏi
        questionList = loadQuestionsFromAssets();

        // kiểm tra câu hỏi
        if (questionList != null) {
            userAnswers = new ArrayList<>(questionList.size()); // Danh sách câu trả lời người dùng
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null); // Thêm mới câu trả lời trống vào danh sách
            }

            // Hiển thị danh sách câu hỏi
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Tạo danh sách lưu câu trả lời người dùng
            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                userAnswers.set(position, selectedAnswer);
                Log.d("Quiz2_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer);
            });
            recyclerView.setAdapter(adapter);

            startQuizTimer(); // Bắt đầu đếm giờ khi quiz được tải

        } else {
            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }

        submitButton.setOnClickListener(v -> submitQuiz()); // Gán Listener cho nút nộp bài
    }

    private void startQuizTimer() {
        countDownTimer = new CountDownTimer(QUIZ_DURATION_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Chuyển mili giây thành phút và giây
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes);
                // Định dạng đồng hồ
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                Toast.makeText(Quiz2_Activity.this, "Hết giờ! Tự động nộp bài.", Toast.LENGTH_LONG).show();
                submitQuiz(); // Tự động nộp bài khi hết giờ
            }
        }.start();
    }

    // Nộp bài
    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng timer khi quiz được nộp
        }

        int correctAnswers = adapter.getCorrectAnswersCount(); // Lấy số lượng câu hỏi đúng
        int totalQuestions = questionList.size();

        // Chuyển trang và truyền dữ liệu
        Intent intent = new Intent(this, Answer_Activity.class);
        intent.putExtra("correctAnswers", correctAnswers); // Truyền số câu đúng
        intent.putExtra("totalQuestions", totalQuestions); // Truyền tổng số câu
        startActivity(intent);
        finish(); // Kết thúc Activity sau khi nộp bài
    }

    // Loại bỏ các thành phần khi đã nộp bài
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
            // Đọc file json từ thư mục Assets
            InputStream doc_file = getAssets().open("quiz_2.json");
            int size = doc_file.available();
            byte[] buffer = new byte[size]; // Tạo mảng lưu trữ câu hỏi
            int read = doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8"); // Chứa nội dung file json
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Chuyển thành mãng, mỗi câu là 1 phần tử trong mảng


            // Duyệt qua từng câu hỏi để lấy các thành phần
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question");
                JSONArray opts = cau_hoi.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j)); // Tạo mới câu trả lời và thêm vào danh sách câu trả lời
                }
                String image = cau_hoi.getString("image");
                String answer = cau_hoi.getString("answer");
                questions.add(new All_Question(qText, options, answer, image)); // Tạo mới câu hỏi và thêm vào danh sách câu hỏi
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}