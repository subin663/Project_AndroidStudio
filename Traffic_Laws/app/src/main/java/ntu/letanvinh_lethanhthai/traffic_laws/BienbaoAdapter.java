package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BienbaoAdapter extends RecyclerView.Adapter<BienbaoAdapter.itemBienbaoHolder> {
    Context context; // Nơi xử lý
    ArrayList<Bienbao> lstBienbao;
    AdapterView.OnItemClickListener onItemClickListener;

    // Tạo Adapter nhận data hiển thị
    public BienbaoAdapter(Context context, ArrayList<Bienbao> lstBien) {
        this.context = context;
        this.lstBienbao = lstBien;
    }

    // Tạo layout cho từng Biển báo
    @NonNull
    @Override
    public itemBienbaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater bien_bao = LayoutInflater.from(context);
        View vItem = bien_bao.inflate(R.layout.item_bienbao, parent, false); // chưa gắn View vào phần hiển thị
        return new itemBienbaoHolder(vItem); // trả về các view của TextView, ImageView
    }

    @Override
    public void onBindViewHolder(@NonNull itemBienbaoHolder holder, int position) // dựa vào vị trí position để gán data cho từng thành phần
    {
        //Lấy vị trí đối tượng hiển thị
        Bienbao bbHienthi = lstBienbao.get(position);
        //Trích thông tin chi tiết
        String ctTenbien = bbHienthi.getTen_bienbao();
        String ctMota = bbHienthi.getMota_bienbao();
        String tenanh = bbHienthi.getBbFileName();

        //Đặt các trường thông tin của holder
        holder.tvTenBien.setText(ctTenbien);

        // Thiết lập maxLines và ellipsize cho tvMota
        // Thiết lặp giới hạn số dòng mô tả và thu gọn văn bản dài
        holder.tvMota.setMaxLines(3);
        holder.tvMota.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvMota.setText(ctMota);

        //Đặt ảnh
        String packageName = holder.itemView.getContext().getPackageName();
        int imageID = holder.itemView.getResources().getIdentifier(
                tenanh, // Tên file ảnh
                "mipmap", // nơi lưu ảnh ha1.png
                packageName);
        holder.ivBienbao.setImageResource(imageID);

        //Bắt sự kiện click item (ví dụ chuyển sang chi tiết biển báo)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BienbaoActivity_Detail.class); // Chuyển trang để xem chi tiết Biển báo
            intent.putExtra("bbData", bbHienthi); // Truyền dữ liệu biển báo
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lstBienbao.size(); // số lượng biển báo
    }

    // Các thành phần trong biển báo
    static class itemBienbaoHolder extends RecyclerView.ViewHolder {
        TextView tvTenBien;
        TextView tvMota;
        ImageView ivBienbao;

        public itemBienbaoHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBien = itemView.findViewById(R.id.tvTenbien);
            tvMota = itemView.findViewById(R.id.tvMota);
            ivBienbao = itemView.findViewById(R.id.ivBienbao);
        }
    }
}