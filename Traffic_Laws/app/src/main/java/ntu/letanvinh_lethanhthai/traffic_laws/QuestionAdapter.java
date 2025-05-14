//package ntu.letanvinh_lethanhthai.traffic_laws;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip.Inflater;
//
//public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ItemQuestionHolder>{
//
//    Context context;
//    ArrayList<All_Question> lstData;
//
//    public QuestionAdapter(Context context, ArrayList<All_Question> lstData) {
//        this.context = context;
//        this.lstData = lstData;
//    }
//
//    @NonNull
//    @Override
//    public ItemQuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       LayoutInflater  caibom = LayoutInflater.from(context);
//       View vItem = caibom.inflate(R.layout.hienthicauhoi, parent,false);
//       ItemQuestionHolder holderCreated = new ItemQuestionHolder(vItem);
//        return holderCreated;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemQuestionHolder holder, int position) {
//        All_Question allQuestionHienthi = lstData.get(position);
//        String question = allQuestionHienthi.getQuestion();
//        List<String> option = allQuestionHienthi.getOptions();
//        String answer = allQuestionHienthi.getAnswer();
//// đặt trường thông tin của Holder
//        holder.tvquestion.setText(question);
//        holder.tvoption.setText((CharSequence) option);
//        holder.tvanswer.setText(answer);
//    }
//
//    @Override
//    public int getItemCount() {
//        return lstData.size();
//    }
//
//    //1.
//    class ItemQuestionHolder extends RecyclerView.ViewHolder{
//        TextView tvquestion;
//        TextView tvoption;
//        TextView tvanswer;
//        public ItemQuestionHolder(@NonNull View itemView) {
//            super(itemView);
//            tvquestion = itemView.findViewById(R.id.question_text);
//            tvoption = itemView.findViewById(R.id.options_text);
//            tvanswer = itemView.findViewById(R.id.answer_text);
//
//        }
//    }
//}

package ntu.letanvinh_lethanhthai.traffic_laws;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<All_Question> questionList;

    public QuestionAdapter(List<All_Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout item_question.xml và tạo ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hienthicauhoi, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        // Lấy câu hỏi tại vị trí 'position'
        All_Question question = questionList.get(position);


        // Gán câu hỏi vào TextView
        holder.questionText.setText(question.getQuestion());

        // Hiển thị các lựa chọn (dạng chuỗi, nối các lựa chọn lại)
        holder.optionsText.setText(TextUtils.join("\n", question.getOptions()));

        // Hiển thị câu trả lời (có thể là đáp án đúng)
        holder.answerText.setText("Đáp án: " + question.getAnswer());

// Hiển thị hình ảnh
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
        return questionList.size();  // Số lượng câu hỏi
    }

    // ViewHolder để lưu trữ các view trong mỗi item
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
