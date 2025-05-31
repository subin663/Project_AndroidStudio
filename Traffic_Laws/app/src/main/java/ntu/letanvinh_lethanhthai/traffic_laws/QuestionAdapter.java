package ntu.letanvinh_lethanhthai.traffic_laws;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Hiện thị câu hỏi cho phần Ôn tập
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    List<All_Question> questionList;

    // Khởi tạo Adapter
    public QuestionAdapter(List<All_Question> questionList) {
        this.questionList = questionList;
    }

    // Tạo layout cho từng câu hỏi
    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout item_question.xml và tạo ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hienthicauhoi, parent, false); // false: chưa gắn view vào parent tránh gắn 2 lần
        return new QuestionViewHolder(view);  // Trả về các View
    }

    // Gắn dữ liệu vào từng thành phần trong View
    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        // Lấy câu hỏi tại vị trí 'position'
        All_Question question = questionList.get(position);

        // Gán câu hỏi vào TextView
        holder.questionText.setText(question.getQuestion());

        // Hiển thị các lựa chọn câu trả lời
        // nối các lựa chọn thành 1 chuỗi, mỗi dòng 1 đáp án
        holder.optionsText.setText(TextUtils.join("\n", question.getOptions()));

        // Hiển thị đáp án
        holder.answerText.setText("Đáp án: " + question.getAnswer());

        // Hiển thị hình ảnh
        String imageName = question.getImage(); // Lấy tên ảnh
        // Kiểm tra sự tồn tại của Hình ảnh
        if (imageName != null && !imageName.trim().isEmpty()) // nếu có tên ảnh thì tìm trong drawable
        {
            // Lấy id của ảnh trong drawable, nếu không có thì trả về 0
            int anh_ID = holder.itemView.getContext().getResources().getIdentifier(
                    imageName, "drawable", holder.itemView.getContext().getPackageName()
            );

            if (anh_ID != 0) // có id ảnh
            {
                // GẮn ảnh vào imageText và hiển thị lên
                holder.imageText.setVisibility(View.VISIBLE);
                holder.imageText.setImageResource(anh_ID);
            } else {
                // Không tìm thấy ảnh thì ẩn phần ImageView của câu hỏi
                holder.imageText.setVisibility(View.GONE);
            }
        } else
        {   // Không tìm thấy ảnh thì ẩn phần ImageView của câu hỏi
            holder.imageText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return questionList.size();  // Số lượng câu hỏi cần hiển thị
    }

    //lưu trữ các thành phần trong giao diện
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public TextView optionsText;
        public TextView answerText;
        public ImageView imageText;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            optionsText = itemView.findViewById(R.id.options_text);
            answerText = itemView.findViewById(R.id.answer_text);
            imageText = itemView.findViewById(R.id.image_text);
        }
    }
}
