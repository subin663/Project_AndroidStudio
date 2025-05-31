package ntu.letanvinh_lethanhthai.traffic_laws;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class All_QuestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    List<All_Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_question);

        // Đọc dữ liệu câu hỏi
        questionList = loadQuestionsFromAssets();

        // Gắn RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Gắn adapter
        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);
    }


    //------Đọc dữ liệu từ thư mục Assets chứa file questions.json
    private List<All_Question> loadQuestionsFromAssets() {
        List<All_Question> questions = new ArrayList<>(); // tạo danh sách chứa câu hỏi
        try {
            // Tiến hành đọc file trong thư mục Assets
            InputStream doc_file = getAssets().open("questions.json");
            int size = doc_file.available(); // Lấy kích thước file
            byte[] buffer = new byte[size]; // Tạo mảng chứa dữ liệu theo kích thước
            doc_file.read(buffer);
            doc_file.close();
            String  noi_dung_file_json = new String(buffer, "UTF-8"); // chứa nội dung file json theo kho buffer
            JSONArray jsonArray = new JSONArray(noi_dung_file_json); // Chuyển json thành mảng, mỗi câu hỏi là 1 phần tử trong mảng

            // // Duyệt qua từng câu hỏi trong jsonArray lấy dữ liệu
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject cau_hoi = jsonArray.getJSONObject(i);
                String qText = cau_hoi.getString("question"); // Biến chứa chuỗi kí tự câu hỏi
                JSONArray opts = cau_hoi.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < opts.length(); j++) {
                    options.add(opts.getString(j)); // Tạo đổi tượng option mới và thêm vào danh sách
                }
                String answer = cau_hoi.getString("answer");
                String image = cau_hoi.getString("image");
                questions.add(new All_Question(qText, options, answer, image)); // Tạo mới câu hỏi và thêm vào danh sách CH
            }
        }
        // Xử lý nếu code try có lỗi
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return questions; /// trả về all các phần của câu hỏi
    }
}
