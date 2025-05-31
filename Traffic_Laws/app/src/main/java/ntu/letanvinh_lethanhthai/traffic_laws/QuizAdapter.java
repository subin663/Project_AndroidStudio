package ntu.letanvinh_lethanhthai.traffic_laws;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuestionViewHolder> {
    List<All_Question> questionList;
    List<String> userAnswers;
    OnAnswerSelectedListener listener;
    int correctAnswersCount = 0;

    public QuizAdapter(List<All_Question> questionList, List<String> userAnswers, OnAnswerSelectedListener listener) {
        this.questionList = questionList;
        this.userAnswers = userAnswers;
        this.listener = listener;
    }
    //Interface này cho phép Activity hoặc Fragment lắng nghe sự kiện khi người dùng chọn một đáp án trong
    //danh sách câu hỏi. Phương thức này nhận vào vị trí của câu hỏi và đáp án đã chọn.
    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int position, String selectedAnswer);
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hienthicauhoi_quiz1, parent, false);
        Log.d("DEBUG", "onCreateViewHolder called");
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, int position) {
        Log.d("DEBUG", "onBindViewHolder called for position: " + position);
        All_Question question = questionList.get(position);
        holder.questionText.setText(question.getQuestion());

        // Xóa tất cả các RadioButton cũ trước khi thêm mới
        holder.optionsGroup.removeAllViews();
        Log.d("DEBUG", "Cleared existing RadioButtons from optionsGroup");

        // Thêm các RadioButton cho mỗi lựa chọn
        if (question.getOptions() != null) {
            for (int i = 0; i < question.getOptions().size(); i++) {
                String option = question.getOptions().get(i);
                RadioButton radioButton = new RadioButton(holder.itemView.getContext());
                radioButton.setText(option);
                holder.optionsGroup.addView(radioButton);

                // Kiểm tra nếu đây là câu trả lời đã được chọn trước đó
                if (userAnswers.get(position) != null && userAnswers.get(position).equals(option)) {
                    radioButton.setChecked(true);
                    Log.d("DEBUG", "RadioButton checked for option: " + option + " at position: " + position);
                }
            }

            holder.optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = group.findViewById(checkedId);
                    if (checkedRadioButton != null) {
                        String selectedAnswerFullText = checkedRadioButton.getText().toString();
                        String selectedAnswerKey = ""; // Biến để lưu 'A', 'B', 'C', hoặc 'D'

                        // Trích xuất ký tự đầu tiên nếu nó là một chữ cái viết hoa
                        if (selectedAnswerFullText.length() > 0 && Character.isUpperCase(selectedAnswerFullText.charAt(0))) {
                            selectedAnswerKey = String.valueOf(selectedAnswerFullText.charAt(0));
                        }

                        int currentPosition = holder.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            userAnswers.set(currentPosition, selectedAnswerFullText); // Lưu toàn bộ văn bản đã chọn
                            Log.d("DEBUG", "Câu " + (currentPosition + 1) + " - Đã chọn: " + selectedAnswerFullText + " stored at position: " + currentPosition);
                            listener.onAnswerSelected(currentPosition, selectedAnswerFullText); // Gọi listener

                            // Kiểm tra và tăng biến đếm bằng cách so sánh ký tự đầu tiên
                            if (selectedAnswerKey.equals(questionList.get(currentPosition).getAnswer())) {
                                correctAnswersCount++;
                                Log.d("DEBUG", "Correct answer at position " + currentPosition + ". Total correct answers: " + correctAnswersCount);
                            }
                        }
                    }
                }
            });
        }

        holder.answerText.setText("");

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
        return questionList.size();
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public RadioGroup optionsGroup;
        public TextView answerText;
        public ImageView imageText;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            optionsGroup = itemView.findViewById(R.id.options_group);
            answerText = itemView.findViewById(R.id.answer_text);
            imageText = itemView.findViewById(R.id.image_text);
        }
    }
}
