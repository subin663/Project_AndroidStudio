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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuizRandom_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    TextView timerTextView;
    CountDownTimer countDownTimer;

    static final long QUIZ_DURATION_MILLIS = 1140000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        // Tìm điều khiển
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timer_text);

        // Tải câu hỏi
        questionList = loadRandomQuestionsFromAssets();

        if (questionList != null && !questionList.isEmpty()) {
            userAnswers = new ArrayList<>(questionList.size());
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null);
            }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                userAnswers.set(position, selectedAnswer);
                Log.d("QuizRandom_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer);
            });
            recyclerView.setAdapter(adapter);

            startQuizTimer();

        } else {

            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại file JSON.", Toast.LENGTH_LONG).show();
            finish();
        }

        submitButton.setOnClickListener(v -> submitQuiz());
    }

    private void startQuizTimer() {
        countDownTimer = new CountDownTimer(QUIZ_DURATION_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes);
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                Toast.makeText(QuizRandom_Activity.this, "Hết giờ! Tự động nộp bài.", Toast.LENGTH_LONG).show();
                submitQuiz();
            }
        }.start();
    }

    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        int correctAnswers = adapter.getCorrectAnswersCount();
        int totalQuestions = questionList.size();

        Intent intent = new Intent(this, Answer_Activity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", totalQuestions);
        intent.putExtra("quizType", "randomQuiz");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // Đọc dữ liệu từ thư mục Assets
    private List<All_Question> loadRandomQuestionsFromAssets() {
        List<All_Question> allQuestions = new ArrayList<>();
        try {
            InputStream doc_file = getAssets().open("questions.json");
            int size = doc_file.available(); // Lấy kích thước để tạo mảng buffer
            byte[] buffer = new byte[size]; // Tạo mảng chứa câu hỏi
            doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8"); // CHứa câu hỏi theo mảng buffer, chuẩn hoá mã hoá kí tự Tiếng Việt

            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Mỗi câu hỏi là 1 phần tử trong mảng

            // Duyệt qua các câu hỏi
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question"); // Lấy tiêu đề câu hỏi
                JSONArray opts = cau_hoi.getJSONArray("options"); // Lấy các lựa chọn trả lời
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j)); // Thêm mới lựa chọn vào danh sách lựa chọn
                }
                String image = cau_hoi.getString("image"); // Lấy hình ảnh
                String answer = cau_hoi.getString("answer"); // Lấy đáp án
                allQuestions.add(new All_Question(qText, options, answer, image)); // Thêm mới câu hỏi vào danh sách câu hỏi
            }

            // Xáo trộn danh sách câu hỏi
            Collections.shuffle(allQuestions);

            // Chỉ lấy số lượng câu hỏi mong muốn
            if (allQuestions.size() >= 25)
            {
                return allQuestions.subList(0, 25);
            } else {
                Log.w("QuizRandom_Activity", "Số câu hỏi trong " + "questions.json" + " (" + allQuestions.size() + ") ít hơn số câu hỏi yêu cầu (" + 25 + "). Trả về tất cả các câu hỏi hiện có.");
                return allQuestions;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}