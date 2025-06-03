package ntu.letanvinh_lethanhthai.traffic_laws;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
    ArrayList<Bienbao> recyclerViewDatas = new ArrayList<>(); // Khởi tạo
    RecyclerView rycyclerViewBB;


    public BienbaoFragment() {
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

        //  loadDataFromSQLite được ghép vào
        //Mở csdl,
        SQLiteDatabase db = requireActivity().openOrCreateDatabase(
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
        // Kết thúc loadDataFromSQLite

        // Thiết lập LayoutManager
        RecyclerView.LayoutManager layoutLinear = new LinearLayoutManager(requireContext());
        rycyclerViewBB.setLayoutManager(layoutLinear);
        // Tạo và thiết lập Adapter với dữ liệu từ SQLite
        bbAdapter = new BienbaoAdapter(requireContext(), recyclerViewDatas);
        rycyclerViewBB.setAdapter(bbAdapter);
    }
}