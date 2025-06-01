package ntu.letanvinh_lethanhthai.traffic_laws;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OT_SaHinhActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    List<All_Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // <-- phải gọi trước
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_question); // <-- phải đặt trước mọi findViewById

        // Đọc dữ liệu câu hỏi
        questionList = loadQuestionsFromAssets();

        // Gắn RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Gắn adapter
        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);
    }
    //----------------
    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>();
        try {
            InputStream doc_file = getAssets().open("ontap_sahinh.json");
            int size = doc_file.available(); // Lấy số lượng câu hỏi để tạo mảng chứa
            byte[] buffer = new byte[size]; // Mảng chứa câu hỏi
            doc_file.read(buffer);
            doc_file.close();
            String noi_dung_file_json = new String(buffer, "UTF-8"); // Chứa nội dung có trong file json theo mảng buffer
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Chuyển json thành mảng, mỗi câu hỏi là 1 phần tử

            // Duyệt qua từng câu hỏi trong jsonArray lấy dữ liệu
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question");
                JSONArray opts = cau_hoi.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j));  // Tạo mới và thêm vào danh sách option
                }
                String answer = cau_hoi.getString("answer");
                String image = cau_hoi.getString("image");
                questions.add(new All_Question(qText, options, answer, image)); // Tạo mới và thêm vào danh sách câu hỏi
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}
