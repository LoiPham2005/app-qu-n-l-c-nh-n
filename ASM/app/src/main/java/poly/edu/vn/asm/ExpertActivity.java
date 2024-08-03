package poly.edu.vn.asm;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import poly.edu.vn.asm.mainscreen.fragment.AlarmFragment;
import poly.edu.vn.asm.mainscreen.fragment.ChatsFragment;
import poly.edu.vn.asm.mainscreen.fragment.HomeFragment;
import poly.edu.vn.asm.mainscreen.fragmentAdmin.AdminSettingFragment;
import poly.edu.vn.asm.mainscreen.fragmentExpert.ExpertSettingFragment;

public class ExpertActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigationExpert);
        frameLayout = findViewById(R.id.frameLayoutExpert);

        // Đặt màn hình mặc định
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutExpert, new HomeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;
            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_chats) {
                selectedFragment = new ChatsFragment();
            } else if (itemId == R.id.nav_alarm) {
                selectedFragment = new AlarmFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new ExpertSettingFragment();
            }

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.frameLayoutExpert, selectedFragment).commit();
            return true;
        });
    }
}