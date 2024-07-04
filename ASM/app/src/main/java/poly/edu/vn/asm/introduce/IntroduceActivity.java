package poly.edu.vn.asm.introduce;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.introduce.slider.SliderPagerAdapter;

public class IntroduceActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    SliderPagerAdapter sliderPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_introduce);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.view_pager_introduce);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        sliderPagerAdapter = new SliderPagerAdapter(this);
        viewPager2.setAdapter(sliderPagerAdapter);

        new TabLayoutMediator(tabLayout,viewPager2,(tab, i) -> {
            tab.setText(SliderPagerAdapter.tabTitles[i]);
        }).attach();



    }
}