package ntu.letanvinh_lethanhthai.traffic_laws;

import java.util.List;

public class All_Question {
    // Các tham số cho câu hỏi
    String question;
    List<String> options;
    String answer;
    String image;
    boolean isCritical;

    // Constructor cũ của bạn, thêm isCritical
    public All_Question(String question, List<String> options, String answer, String image, boolean isCritical) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.image = image;
        this.isCritical = isCritical;
    }


    // Hàm khởi tạo có tham số
    public All_Question(String question, List<String> options, String answer, String image) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.image = image;
        this.isCritical = false; // Mặc định là false nếu không được cung cấp
    }


    // Các phương thức getter lấy dữ liệu
    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() { // Giữ nguyên tên
        return answer;
    }

    public String getImage() {
        return image;
    }

    public boolean isCritical() { // Getter cho isCritical
        return isCritical;
    }
}