package ntu.letanvinh_lethanhthai.traffic_laws;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BienbaoActivity_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bienbao_detail);
        TextView txtTenbien = findViewById(R.id.txtTenbien);
        TextView txtDescription = findViewById(R.id.txtMota);
        ImageView imgBien = findViewById(R.id.imgBienbao);

        // Nhận dữ liệu từ Intent
        Bienbao bb = (Bienbao) getIntent().getSerializableExtra("bbData");

        if (bb != null) {
            txtTenbien.setText(bb.getTen_bienbao());
            txtDescription.setText( bb.getMota_bienbao());

            String packageName = getPackageName();
            int bienbaoID = getResources().getIdentifier(bb.getBbFileName(), "mipmap", packageName);
            imgBien.setImageResource(bienbaoID);
        }
    }
}