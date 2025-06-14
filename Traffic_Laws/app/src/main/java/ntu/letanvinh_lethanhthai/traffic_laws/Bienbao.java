package ntu.letanvinh_lethanhthai.traffic_laws;
import java.io.Serializable;
public class Bienbao implements Serializable  // Cho phép truyền qua các Activity bằng intent
{
    static final long serialVersionUID = 1L; // Đảm bảo data không bị nhầm khi truyền đi, gán giá trị 1 kiểu long
    int id_bienbao;
    String ten_bienbao;
    String mota_bienbao;
    String wbbFileName; // Tên file ảnh

    // Hàm khởi tạo có tham số
    public Bienbao(int id_bienbao, String ten_bienbao, String mota_bienbao, String wbbFileName) {
        this.id_bienbao = id_bienbao;
        this.ten_bienbao = ten_bienbao;
        this.mota_bienbao = mota_bienbao;
        this.wbbFileName = wbbFileName;
    }
    // Phương thức getter và setter để lấy và gán giá trị
    public int getId_bienbao() {
        return id_bienbao;
    }

    public void setId_bienbao(int id_bienbao) {
        this.id_bienbao = id_bienbao;
    }

    public String getTen_bienbao() {
        return ten_bienbao;
    }

    public void setTen_bienbao(String ten_bienbao) {
        this.ten_bienbao = ten_bienbao;
    }

    public String getMota_bienbao() {
        return mota_bienbao;
    }

    public void setMota_bienbao(String mota_bienbao) {
        this.mota_bienbao = mota_bienbao;
    }
    public String getBbFileName() {
        return wbbFileName;
    }

    public void setBbFileName(String wbbFileName) {
        this.wbbFileName = wbbFileName;
    }
}