package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class OntapFragment extends Fragment {
    LinearLayout Toanbocauhoi;
    LinearLayout Khainiemquytacgiaothong, Bienbaoduongbo, Sahinh, Cauhoiliet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ontap, container, false);

        //Tìm kiếm đối tượng
        Toanbocauhoi = view.findViewById(R.id.Toanbocauhoi);
        Khainiemquytacgiaothong = view.findViewById(R.id.Khainiemquytacgiaothong);
        Bienbaoduongbo = view.findViewById(R.id.Bienbaoduongbo);
        Sahinh = view.findViewById(R.id.Sahinh);
        Cauhoiliet = view.findViewById(R.id.Cauhoiliet);

        // Thực hiện chức năng chuyển trang qua câu hỏi

        // Trang Tất cả 200 câu hỏi
        Toanbocauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), All_QuestionActivity.class);
                startActivity(intent);
            }
        });

        // Trang Khái niệm và Quy tắc giao thông
        Khainiemquytacgiaothong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OT_QuytacgiaothongActivity.class);
                startActivity(intent);
            }
        });

        // Trang Ôn tập biển báo đường bộ
        Bienbaoduongbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OT_BienbaoduongboActivity.class);
                startActivity(intent);
            }
        });

        // Trang Ôn tập Sa hình
        Sahinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OT_SaHinhActivity.class);
                startActivity(intent);
            }
        });

        // Trang Ôn tập câu điểm liệt
        Cauhoiliet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OT_CauhoilietActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}