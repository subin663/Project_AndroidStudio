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
    // Biến mới để theo dõi lỗi câu hỏi liệt
    boolean hasCriticalError = false;

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

        holder.optionsGroup.removeAllViews();
        Log.d("DEBUG", "Cleared existing RadioButtons from optionsGroup");

        if (question.getOptions() != null) {
            for (int i = 0; i < question.getOptions().size(); i++) {
                String option = question.getOptions().get(i);
                RadioButton radioButton = new RadioButton(holder.itemView.getContext());
                radioButton.setText(option);
                holder.optionsGroup.addView(radioButton);

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
                        // Lấy vị trí hiện tại của ViewHolder
                        int currentPosition = holder.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            All_Question currentQuestion = questionList.get(currentPosition);

                            // Lưu toàn bộ văn bản đã chọn
                            userAnswers.set(currentPosition, selectedAnswerFullText);
                            Log.d("DEBUG", "Câu " + (currentPosition + 1) + " - Đã chọn: " + selectedAnswerFullText + " stored at position: " + currentPosition);
                            listener.onAnswerSelected(currentPosition, selectedAnswerFullText); // Gọi listener

                            // Lấy ký tự đáp án (A, B, C, D) từ văn bản đầy đủ của tùy chọn
                            String selectedAnswerKey = "";
                            if (selectedAnswerFullText.length() > 0 && Character.isUpperCase(selectedAnswerFullText.charAt(0))) {
                                selectedAnswerKey = String.valueOf(selectedAnswerFullText.charAt(0));
                            }

                            // Cập nhật số câu trả lời đúng
                            // Cần reset correctAnswersCount và hasCriticalError mỗi khi user thay đổi đáp án
                            // để tính toán lại chính xác
                            recalculateResults();
                        }
                    }
                }
            });
        }

        holder.answerText.setText("");

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

    // Phương thức mới để tính toán lại kết quả sau mỗi lần thay đổi đáp án
    public void recalculateResults() {
        correctAnswersCount = 0;
        hasCriticalError = false;

        for (int i = 0; i < questionList.size(); i++) {
            All_Question question = questionList.get(i);
            String userAnswer = userAnswers.get(i); // Lấy toàn bộ văn bản câu trả lời của người dùng

            if (userAnswer != null) {
                // Trích xuất ký tự đáp án (A, B, C, D) từ câu trả lời của người dùng
                String userAnswerKey = "";
                if (userAnswer.length() > 0 && Character.isUpperCase(userAnswer.charAt(0))) {
                    userAnswerKey = String.valueOf(userAnswer.charAt(0));
                }

                // Kiểm tra xem câu trả lời có đúng không
                if (userAnswerKey.equals(question.getAnswer())) {
                    correctAnswersCount++;
                } else {
                    // Nếu là câu hỏi liệt và trả lời sai
                    if (question.isCritical()) {
                        hasCriticalError = true;
                        Log.d("QuizAdapter", "Critical question answered incorrectly at position: " + i);
                    }
                }
            }
        }
        Log.d("QuizAdapter", "Recalculated: Correct Answers = " + correctAnswersCount + ", Critical Error = " + hasCriticalError);
    }

    public int getCorrectAnswersCount() {
        // Đảm bảo rằng kết quả được tính toán lại trước khi trả về
        recalculateResults();
        return correctAnswersCount;
    }

    public boolean hasCriticalError() {
        // Đảm bảo rằng kết quả được tính toán lại trước khi trả về
        recalculateResults();
        return hasCriticalError;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public RadioGroup optionsGroup;
        public TextView answerText; // This might not be needed for quiz view, but keep if used elsewhere
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