package ntu.letanvinh_lethanhthai.traffic_laws;

import java.util.List;

public class All_Question {
    // Các tham số cho câu hỏi
    String question;
    List<String> options;
    String answer;
    String image;

    // Hàm khởi tạo có tham số
    public All_Question(String question, List<String> options, String answer, String image) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.image = image;
    }

    // Các phương thức getter lấy dữ liệu
    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public String getImage() {
        return image;
    }
}
