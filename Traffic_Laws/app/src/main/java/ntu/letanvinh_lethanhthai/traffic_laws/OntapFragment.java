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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ontap, container, false);
        Toanbocauhoi = view.findViewById(R.id.Toanbocauhoi);
        Toanbocauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), All_QuestionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}