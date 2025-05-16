package ntu.letanvinh_lethanhthai.traffic_laws;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Quiz1_Activity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private List<All_Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz1);

        Log.d("DEBUG", "All_QuestionActivity được khởi chạy");

        // Đọc dữ liệu câu hỏi
        questionList = loadQuestionsFromAssets();

        // In log số lượng câu hỏi
        Log.d("DEBUG", "Số câu hỏi load: " + questionList.size());

        // Gắn RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Gắn adapter
        adapter = new QuizAdapter(questionList);
        recyclerView.setAdapter(adapter);
    }
    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {
            InputStream is = getAssets().open("quiz_1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String qText = obj.getString("question");
                JSONArray opts = obj.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j));
                }
                String image = obj.getString("image");
                questions.add(new All_Question(qText, options, null, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}