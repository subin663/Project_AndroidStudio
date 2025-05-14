package ntu.letanvinh_lethanhthai.traffic_laws;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BienbaoFragment extends Fragment {
    BienbaoAdapter bbAdapter;
    ArrayList<Bienbao> recyclerViewDatas = new ArrayList<>(); // Khởi tạo ở đây
    RecyclerView rycyclerViewBB;
    

    public BienbaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bienbao, container, false);
        // Tìm RecyclerView trong layout của Fragment
        rycyclerViewBB = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // **Logic loadDataFromSQLite được ghép vào đây**
        //Mở csdl,
        SQLiteDatabase db = getActivity().openOrCreateDatabase(
                "QLBienbao.db", // Tên của cơ sở dữ liệu
                Context.MODE_PRIVATE, // Chế độ truy cập
                null             // CursorFactory
        );
        //B2:thực thi câu lệnh select
        String sqlSelect ="SELECT * FROM Bienbaos;";
        Cursor cs = db.rawQuery(sqlSelect, null);

        ArrayList<Bienbao> dsBien = new ArrayList<Bienbao>();
        while (cs.moveToNext()) {
            int idBien = cs.getInt(cs.getColumnIndexOrThrow("IdBien"));
            String tenBienbao = cs.getString(cs.getColumnIndexOrThrow("TenBienbao"));
            String motaBienbao = cs.getString(cs.getColumnIndexOrThrow("MotaBienbao"));
            String fileName = cs.getString(cs.getColumnIndexOrThrow("FileName"));

            Bienbao bienbao = new Bienbao(idBien, tenBienbao, motaBienbao, fileName);
            recyclerViewDatas.add(bienbao);
        };
        cs.close();
        db.close();
        // **Kết thúc logic loadDataFromSQLite**

        // Thiết lập LayoutManager
        RecyclerView.LayoutManager layoutLinear = new LinearLayoutManager(requireContext());
        rycyclerViewBB.setLayoutManager(layoutLinear);
        // Tạo và thiết lập Adapter với dữ liệu từ SQLite
        bbAdapter = new BienbaoAdapter(requireContext(), recyclerViewDatas);
        rycyclerViewBB.setAdapter(bbAdapter);
    }

    // Bạn không cần phương thức getDataForRecyclerView() nữa nếu bạn chỉ muốn hiển thị dữ liệu từ SQLite
//    ArrayList<Bienbao> getDataForRecyclerView() {
//        ArrayList<Bienbao> dsDuLieu = new ArrayList<>();
//        dsDuLieu.add(new Bienbao(1, "Cấm đi ngược chiều", "ZukaBear là một chú gấu mạnh mẽ", "img_1"));
//        dsDuLieu.add(new Bienbao(2, "img_2", "WhiteBear đến từ vùng lạnh giá", "img_2"));
//        dsDuLieu.add(new Bienbao(3, "img_3", "ZukaBearChild là phiên bản nhỏ của ZukaBear", "img_3"));
//        dsDuLieu.add(new Bienbao(4, "img_4", "WhiteBear2 có bộ lông trắng muốt", "img_4"));
//        dsDuLieu.add(new Bienbao(5, "img_5", "BrownBear2 là chú gấu nâu đáng yêu", "img_5"));
//        dsDuLieu.add(new Bienbao(6, "img_1", "ZukaBear dũng mãnh và kiên cường", "img_1"));
//        dsDuLieu.add(new Bienbao(7, "img_3", "ZukaBearChild đang học cách trở thành chiến binh", "img_3"));
//        return dsDuLieu;
//    }
}