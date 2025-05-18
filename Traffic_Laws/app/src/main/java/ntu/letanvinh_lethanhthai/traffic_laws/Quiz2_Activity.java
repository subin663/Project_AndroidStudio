package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Quiz2_Activity extends AppCompatActivity  {
    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<All_Question> questionList;
    List<String> userAnswers;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        submitButton = findViewById(R.id.submitButton);

        questionList = loadQuestionsFromAssets();

        // Khởi tạo userAnswers sau khi đã có questionList
        if (questionList != null) {
            userAnswers = new ArrayList<>(questionList.size());
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers.add(null);
            }

            // Gắn RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Gắn adapter
            adapter = new QuizAdapter(questionList, userAnswers, new QuizAdapter.OnAnswerSelectedListener() {
                @Override
                public void onAnswerSelected(int position, String selectedAnswer) {
                    // Lưu trữ câu trả lời của người dùng
                    userAnswers.set(position, selectedAnswer);
                    Log.d("Quiz1_Activity", "Câu " + (position + 1) + " - Đã chọn: " + selectedAnswer);
                }
            });
            recyclerView.setAdapter(adapter);

        } else {
            Log.e("Quiz1_Activity", "Không thể load danh sách câu hỏi.");
            Toast.makeText(this, "Không thể tải được câu hỏi. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
            finish();
        }
        //Ví dụ tính điểm khi người dùng hoàn thành xong
        //Bạn có thể gọi cái này ở bất cứ đâu bạn muốn
        //như là trong 1 button listener
        int finalScore = adapter.getCorrectAnswersCount();
        Log.d("Quiz1_Activity", "Final Score: " + finalScore + " out of " + questionList.size());

        submitButton.setOnClickListener(v -> {
            int correctAnswers = adapter.getCorrectAnswersCount();
            int totalQuestions = questionList.size();

            Intent intent = new Intent(this, Answer_Activity.class);
            intent.putExtra("correctAnswers", correctAnswers); // Truyền số câu đúng
            intent.putExtra("totalQuestions", totalQuestions); // Truyền tổng số câu
            startActivity(intent);
        });
    }

    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {
            InputStream is = getAssets().open("quiz_2.json");
            int size = is.available();
            Log.d("DEBUG", "Size of quiz_1.json: " + size);
            byte[] buffer = new byte[size];
            int read = is.read(buffer);
            Log.d("DEBUG", "Bytes read from quiz_1.json: " + read);
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
            Log.e("Quiz1_Activity", "Error loading questions: ", e);
            e.printStackTrace();
            return null;
        }
        return questions;
    }

}
