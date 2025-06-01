package ntu.letanvinh_lethanhthai.traffic_laws;

import java.util.List;

public class All_Question {
    String question; // Giữ nguyên tên
    List<String> options; // Giữ nguyên tên
    String answer; // Giữ nguyên tên
    String image; // Giữ nguyên tên
    boolean isCritical; // Thêm trường mới

    // Constructor cũ của bạn, thêm isCritical
    public All_Question(String question, List<String> options, String answer, String image, boolean isCritical) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.image = image;
        this.isCritical = isCritical;
    }

    // Constructor cũ của bạn (nếu bạn muốn giữ lại cho trường hợp không có isCritical)
    // Hoặc bạn có thể chỉ dùng constructor ở trên và truyền false cho isCritical khi cần
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

    // Bạn có thể thêm setter nếu cần, ví dụ:
    // public void setCritical(boolean critical) {
    //     isCritical = critical;
    // }
}