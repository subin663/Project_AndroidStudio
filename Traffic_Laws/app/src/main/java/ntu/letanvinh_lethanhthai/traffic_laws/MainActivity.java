package ntu.letanvinh_lethanhthai.traffic_laws;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav=findViewById(R.id.bottomNavigationView);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new ThithuFragment()).commit();
        Toast.makeText(MainActivity.this, "Chọn mục bất kì để thi thử",Toast.LENGTH_LONG).show();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment=null;
                int mnuItemDuocChonID=item.getItemId();

                // Điều chỉnh để hiển thị các trang lên màn hình
                if (mnuItemDuocChonID==R.id.mnu_thithu){
                    //thay fragment
                    // sửa: fragmentManager.beginTransaction().replace(R.id.bottomNavigationView,new ThithuFragment()).commit();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,new ThithuFragment()).commit();
                    Toast.makeText(MainActivity.this, "Chọn mục bất kì để thi thử",Toast.LENGTH_LONG).show();
                }
                else if(mnuItemDuocChonID==R.id.mnu_ontap){
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,new OntapFragment()).commit();
                    Toast.makeText(MainActivity.this,"Chọn mục bất kì để ôn tập",Toast.LENGTH_LONG).show();
                }
                else if(mnuItemDuocChonID==R.id.mnu_bienbao){
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,new BienbaoFragment()).commit();
                    Toast.makeText(MainActivity.this,"Biển báo",Toast.LENGTH_LONG).show();
                }
                else{
                    return true;
                }
                return false;
            }
        });

    }
}