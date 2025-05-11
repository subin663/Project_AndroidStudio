package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BienbaoAdapter extends RecyclerView.Adapter<BienbaoAdapter.itemBienbaoHolder> {
    Context context;
    ArrayList<Bienbao> lstBienbao;
    // AdapterView.OnItemClickListener onItemClickListener; // Bạn có thể sử dụng interface callback thay vì AdapterView.OnItemClickListener

    public BienbaoAdapter(Context context, ArrayList<Bienbao> lstBienbao) {
        this.context = context;
        this.lstBienbao = lstBienbao;
    }

    @NonNull
    @Override
    public itemBienbaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater bien_bao = LayoutInflater.from(context);
        View vItem = bien_bao.inflate(R.layout.item_bienbao, parent, false);
        return new itemBienbaoHolder(vItem);
    }

    @Override
    public void onBindViewHolder(@NonNull itemBienbaoHolder holder, int position) {
        //Lấy đối tượng hiển thị
        Bienbao bbHienthi = lstBienbao.get(position);
        //Trích thông tin
        String ctTenbien = bbHienthi.getTen_bienbao();
        String ctMota = bbHienthi.getMota_bienbao();
        String tenanh = bbHienthi.getBbFileName(); // **Kiểm tra lại phương thức này trong class Bienbao**

        //Đặt các trường thông tin của holder
        holder.tvTenBien.setText(ctTenbien);
        holder.tvMota.setText(ctMota);

        //Đặt ảnh
        String packageName = holder.itemView.getContext().getPackageName();
        int imageID = holder.itemView.getResources().getIdentifier(
                tenanh, // Tên file ảnh (ví dụ: "img_1" nếu file là img_1.jpg trong mipmap)
                "mipmap", // **Thay "mipmap" bằng "drawable" nếu bạn đặt ảnh trong thư mục drawable**
                packageName);
        holder.ivBienbao.setImageResource(imageID);

        //Bắt sự kiện click item (ví dụ chuyển sang chi tiết biển báo)
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ChiTietBienBaoActivity.class); // **Thay ChiTietBienBaoActivity.class bằng Activity bạn muốn chuyển đến**
//            intent.putExtra("bienbao_data", bbHienthi); // Truyền dữ liệu biển báo (đảm bảo Bienbao implements Serializable hoặc Parcelable)
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return lstBienbao.size();
    }

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