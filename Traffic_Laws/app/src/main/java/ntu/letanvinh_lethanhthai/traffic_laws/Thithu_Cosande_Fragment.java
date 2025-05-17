package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Thithu_Cosande_Fragment extends Fragment {

    Button de1,de2,de3,de4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thithu_cosande_, container, false);
        de1=view.findViewById(R.id.btnDe1);
        de2=view.findViewById(R.id.btnDe2);
        de3=view.findViewById(R.id.btnDe3);
        de4=view.findViewById(R.id.btnDe4);

        de1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Quiz1_Activity.class);
                startActivity(intent);
            }
        });
        de2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), Quiz2_Activity.class);
                //startActivity(intent);
            }
        });
        de3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        de4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}