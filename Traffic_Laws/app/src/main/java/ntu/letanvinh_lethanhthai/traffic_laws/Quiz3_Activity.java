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

public class Quiz3_Activity extends AppCompatActivity  {
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
        setContentView(R.layout.activity_quiz1); // Đảm bảo bạn đang sử dụng layout có timer_text

        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timer_text); // Khởi tạo timer TextView

        questionList = loadQuestionsFromAssets();

        if (questionList != null) {
            userAnswers = new ArrayList<>(questionList.size());
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null);
            }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new QuizAdapter(questionList, userAnswers, (position, selectedAnswer) -> {
                userAnswers.set(position, selectedAnswer);
                Log.d("Quiz3_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer); // Đổi tên Activity trong Log
            });
            recyclerView.setAdapter(adapter);

            startQuizTimer(); // Bắt đầu đếm giờ khi quiz được tải

        } else {
            Log.e("Quiz3_Activity", "Không thể load danh sách câu hỏi."); // Đổi tên Activity trong Log
            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }

        submitButton.setOnClickListener(v -> submitQuiz()); // Gán Listener cho nút nộp bài
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
                Toast.makeText(Quiz3_Activity.this, "Hết giờ! Tự động nộp bài.", Toast.LENGTH_LONG).show();
                submitQuiz(); // Tự động nộp bài khi hết giờ
            }
        }.start();
    }

    private void submitQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng timer khi quiz được nộp
        }

        int correctAnswers = adapter.getCorrectAnswersCount();
        int totalQuestions = questionList.size();

        Intent intent = new Intent(this, Answer_Activity.class);
        intent.putExtra("correctAnswers", correctAnswers); // Truyền số câu đúng
        intent.putExtra("totalQuestions", totalQuestions); // Truyền tổng số câu
        startActivity(intent);
        finish(); // Kết thúc Activity sau khi nộp bài
    }

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
            // Chú ý: Ở đây bạn đang tải quiz_3.json
            InputStream is = getAssets().open("quiz_3.json");
            int size = is.available();
            Log.d("DEBUG", "Size of quiz_3.json: " + size); // Đổi tên file trong log
            byte[] buffer = new byte[size];
            int read = is.read(buffer);
            Log.d("DEBUG", "Bytes read from quiz_3.json: " + read); // Đổi tên file trong log
            is.close();
            String json = new String(buffer, "UTF-8");
            Log.d("DEBUG", "JSON content: " + json);
            JSONArray jsonArray = new JSONArray(json);
            Log.d("DEBUG", "JSONArray length: " + jsonArray.length());


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
                questions.add(new All_Question(qText, options, answer, image));
            }
            Log.d("DEBUG", "Number of questions loaded: " + questions.size());
        } catch (Exception e) {
            Log.e("Quiz3_Activity", "Error loading questions: ", e); // Đổi tên Activity trong Log
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}