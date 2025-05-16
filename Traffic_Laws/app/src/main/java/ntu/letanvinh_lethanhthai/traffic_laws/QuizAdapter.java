package ntu.letanvinh_lethanhthai.traffic_laws;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuestionViewHolder> {
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
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
