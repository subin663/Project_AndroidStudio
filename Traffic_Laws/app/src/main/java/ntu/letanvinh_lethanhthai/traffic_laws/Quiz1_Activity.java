package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log; // Giữ lại Log nếu bạn dùng để debug
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Quiz1_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    TextView timerTextView;
    CountDownTimer countDownTimer;

    static final long QUIZ_DURATION_MILLIS = 1140000; // 19 minutes (19 * 60 * 1000)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        // Tìm điều khiển
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timer_text); // Nơi hiển thị thời gian
        recyclerView = findViewById(R.id.recyclerView);

        // Tải câu hỏi
        questionList = loadQuestionsFromAssets();

        // Kiểm tra xem câu hỏi có được tải thành công và không rỗng không
        if (questionList != null && !questionList.isEmpty()) {
            userAnswers = new ArrayList<>(questionList.size()); // Tạo mảng chứa câu trả lời người dùng
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null);  // Khởi tạo tất cả câu trả lời là null
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Tạo mới Adapter, danh sách chứa câu trả lời người dùng
            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                // Logic tính toán lại kết quả đã được xử lý chính trong onCheckedChanged của adapter.
                // Không cần làm gì cụ thể ở đây cho câu hỏi liệt,
                // vì adapter sẽ tự xử lý việc theo dõi chúng.
            });
            recyclerView.setAdapter(adapter);

            // Thời gian bắt đầu đếm
            startQuizTimer();

        } else {
            Toast.makeText(this, "Không thể tải được câu hỏi hoặc danh sách rỗng. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Nộp bài
        submitButton.setOnClickListener(v -> submitQuiz());
    }

    // Bộ đếm thời gian
    private void startQuizTimer() {
        countDownTimer = new CountDownTimer(QUIZ_DURATION_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Chuyển mili giây sang phút và giây
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes);
                // Định dạng kiểu đồng hồ
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                Toast.makeText(Quiz1_Activity.this, "Hết giờ! Tự động nộp bài.", Toast.LENGTH_LONG).show();
                submitQuiz();
            }
        }.start();
    }

    private void submitQuiz() {
        // Khi đã nộp bài thì thời gian còn lại sẽ huỷ bỏ
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Gọi recalculateResults() một lần cuối cùng để đảm bảo kết quả chính xác nhất
        // trước khi gửi đi.
        adapter.recalculateResults();

        boolean hasCriticalError = adapter.hasCriticalError(); // Lấy trạng thái lỗi câu hỏi liệt
        int correctAnswers = adapter.getCorrectAnswersCount(); // Lấy các câu trả lời đúng
        int totalQuestions = questionList.size(); // số lượng câu hỏi


        // chuyển trang
        Intent intent = new Intent(this, Answer_Activity.class);
        // Truyền dữ liệu
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", totalQuestions);
        // Thêm thông tin về lỗi câu hỏi liệt vào Intent
        intent.putExtra("hasCriticalError", hasCriticalError);
        startActivity(intent);
        finish();
    }

    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {
            InputStream doc_file = getAssets().open("quiz_1.json"); // đọc file từ thu mục Assets
            int size = doc_file.available(); // lấy số lượng câu hỏi để tạo mảng
            byte[] buffer = new byte[size]; // Mãng chứa các câu hỏi
            int read = doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); //Chuyển json thành mảng, mỗi câu hỏi là 1 phần tử


            // Duyệt qua các câu hỏi trong mảng json để lấy dữ liệu
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String qText = obj.getString("question");
                JSONArray opts = obj.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j));
                }
                String image = obj.getString("image");
                String answer = obj.getString("answer");
                // Đọc giá trị isCritical từ JSON, mặc định là false nếu không có
                boolean isCritical = obj.optBoolean("isCritical", false);
                questions.add(new All_Question(qText, options, answer, image, isCritical)); // Thêm mới câu hỏi vào danh sách câu hỏi
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}