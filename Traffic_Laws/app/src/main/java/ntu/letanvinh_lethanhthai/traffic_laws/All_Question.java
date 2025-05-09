package ntu.letanvinh_lethanhthai.traffic_laws;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class All_Question {
    private String question;
    private List<String> options;
    private String answer;

    public All_Question(String question, List<String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
