package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

public class Quiz2_Activity extends AppCompatActivity  {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    static final long QUIZ_DURATION_MILLIS = 1140000; // 19 phút (19 * 60 * 1000)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1); // Sử dụng layout activity_quiz1, đảm bảo nó có timer_text và submitButton

        // Tìm kiếm các View
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timer_text);
        recyclerView = findViewById(R.id.recyclerView);


        // Tải câu hỏi
        questionList = loadQuestionsFromAssets();

        // Kiểm tra xem câu hỏi đã được tải thành công chưa
        if (questionList != null && !questionList.isEmpty()) {
            userAnswers = new ArrayList<>(questionList.size()); // Danh sách để lưu câu trả lời của người dùng
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null); // Khởi tạo với null cho mỗi câu hỏi
            }

            // Thiết lập RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Tạo adapter, truyền danh sách câu hỏi, câu trả lời của người dùng và một listener
            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                // Logic câu hỏi điểm liệt và tính toán lại điểm số hiện được xử lý trong adapter.
                // Chúng ta chỉ cần đảm bảo rằng câu trả lời được chọn của người dùng được lưu trữ.
                userAnswers.set(position, selectedAnswer);
                Log.d("Quiz2_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer);
            });
            recyclerView.setAdapter(adapter);

            startQuizTimer(); // Bắt đầu đếm giờ khi bài kiểm tra được tải

        } else {
            Log.e("Quiz2_Activity", "Không thể tải danh sách câu hỏi.");
            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }

        submitButton.setOnClickListener(v -> submitQuiz()); // Đặt OnClickListener cho nút nộp bài
    }

    private void startQuizTimer() {
        countDownTimer = new CountDownTimer(QUIZ_DURATION_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Chuyển đổi mili giây thành phút và giây
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes);
                // Định dạng thời gian và hiển thị
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

    // Nộp bài kiểm tra
    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng bộ hẹn giờ khi bài kiểm tra được nộp
        }

        // Đảm bảo kết quả được tính toán lại lần cuối trước khi nộp
        adapter.recalculateResults();

        int correctAnswers = adapter.getCorrectAnswersCount(); // Lấy số câu trả lời đúng
        int totalQuestions = questionList.size();
        // Lấy trạng thái lỗi câu hỏi điểm liệt từ adapter
        boolean hasCriticalError = adapter.hasCriticalError();

        // Chuyển sang Answer_Activity và truyền dữ liệu
        Intent intent = new Intent(this, Answer_Activity.class);
        intent.putExtra("correctAnswers", correctAnswers); // Truyền số câu trả lời đúng
        intent.putExtra("totalQuestions", totalQuestions); // Truyền tổng số câu hỏi
        intent.putExtra("hasCriticalError", hasCriticalError); // Truyền trạng thái lỗi câu hỏi điểm liệt
        startActivity(intent);
        finish(); // Kết thúc Activity này sau khi nộp
    }

    // Dọn dẹp tài nguyên khi Activity bị hủy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Đảm bảo bộ hẹn giờ được hủy để tránh rò rỉ bộ nhớ
        }
    }

    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {
            // Đọc tệp JSON từ thư mục Assets
            InputStream doc_file = getAssets().open("quiz_2.json");
            int size = doc_file.available();
            byte[] buffer = new byte[size]; // Tạo mảng byte để lưu nội dung tệp
            int read = doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8"); // Nội dung của tệp JSON
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Chuyển đổi thành mảng JSON


            // Lặp lại từng câu hỏi trong mảng
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question");
                JSONArray opts = cau_hoi.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j)); // Thêm các tùy chọn vào danh sách
                }
                String image = cau_hoi.getString("image");
                String answer = cau_hoi.getString("answer");
                // Đọc giá trị isCritical từ JSON, mặc định là false nếu không có
                boolean isCritical = cau_hoi.optBoolean("isCritical", false);
                questions.add(new All_Question(qText, options, answer, image, isCritical)); // Tạo và thêm câu hỏi mới vào danh sách
            }
        } catch (Exception e) {
            Log.e("Quiz2_Activity", "Lỗi khi tải câu hỏi: ", e);
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}