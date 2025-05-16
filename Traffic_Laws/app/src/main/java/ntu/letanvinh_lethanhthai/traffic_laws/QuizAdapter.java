package ntu.letanvinh_lethanhthai.traffic_laws;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuestionViewHolder> {
    private List<All_Question> questionList;

    public QuizAdapter(List<All_Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hienthicauhoi, parent, false); // Inflate layout của item
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        All_Question question = questionList.get(position);
        holder.questionText.setText(question.getQuestion());

        // Hiển thị các lựa chọn (nếu có)
        if (question.getOptions() != null) {
            holder.optionsText.setText(TextUtils.join("\n", question.getOptions()));
        } else {
            holder.optionsText.setText(""); // Hoặc một giá trị mặc định khác
        }

        // Hiển thị đáp án (nếu có)
        if (question.getAnswer() != null) {
            holder.answerText.setText("Đáp án: " + question.getAnswer());
        } else {
            holder.answerText.setText(""); // Hoặc một giá trị mặc định khác
        }

        // Hiển thị hình ảnh (nếu có)
        String imageName = question.getImage();
        if (imageName != null && !imageName.trim().isEmpty()) {
            int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                    imageName, "drawable", holder.itemView.getContext().getPackageName()
            );
            if (imageResId != 0) {
                holder.imageText.setVisibility(View.VISIBLE);
                holder.imageText.setImageResource(imageResId);
            } else {
                holder.imageText.setVisibility(View.GONE);
            }
        } else {
            holder.imageText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size(); // Trả về kích thước của danh sách câu hỏi
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public TextView optionsText;
        public TextView answerText;
        public ImageView imageText;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            optionsText = itemView.findViewById(R.id.options_text);
            answerText = itemView.findViewById(R.id.answer_text);
            imageText = itemView.findViewById(R.id.image_text);
        }
    }
}