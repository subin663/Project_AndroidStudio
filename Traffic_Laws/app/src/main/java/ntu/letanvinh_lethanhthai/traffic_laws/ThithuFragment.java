package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ThithuFragment extends Fragment {

    LinearLayout lnThi1;
    LinearLayout lnThi2;
    LinearLayout lnThi3;
    LinearLayout lnThi4;

    public ThithuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thithu, container, false);

        // Tìm tham chiếu đến các LinearLayout bằng ID
        lnThi1 = view.findViewById(R.id.lnThi1);
        lnThi2 = view.findViewById(R.id.lnThi2);
        lnThi3 = view.findViewById(R.id.lnThi3);

        // Thiết lập OnClickListener cho các LinearLayout
        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        lnThi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Thithu_Cosande_Fragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Thithu_Cosande_Fragment())
                        .commit();
            }
        });

        lnThi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizRandom_Activity.class);
                startActivity(intent);
            }
        });

        lnThi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeLiet_Activity.class);
                startActivity(intent);
            }
        });
    }
}