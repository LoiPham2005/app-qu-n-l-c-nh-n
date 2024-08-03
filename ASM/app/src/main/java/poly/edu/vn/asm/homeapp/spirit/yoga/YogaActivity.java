package poly.edu.vn.asm.homeapp.spirit.yoga;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.spirit.yoga.video.VideoIntroActivity;
import poly.edu.vn.asm.homeapp.spirit.yoga.video.VideoLesson1Activity;

public class YogaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnShowVideo1 = findViewById(R.id.btnShowVideo1);
        Button btnShowVideo2 = findViewById(R.id.btnShowVideo2);

        btnShowVideo1.setOnClickListener(v -> {
            startActivity(new Intent(this, VideoIntroActivity.class));
        });

        btnShowVideo2.setOnClickListener(v -> {
            startActivity(new Intent(this, VideoLesson1Activity.class));
        });
    }
}