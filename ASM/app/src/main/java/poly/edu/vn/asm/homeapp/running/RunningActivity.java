package poly.edu.vn.asm.homeapp.running;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.running.run.RunActivity;
import poly.edu.vn.asm.homeapp.running.show.ShowActivity;

public class RunningActivity extends AppCompatActivity {
    Button btnShow, btnRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_running);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnShow = findViewById(R.id.btnShow);
        btnRun = findViewById(R.id.btnRun);

        btnRun.setOnClickListener(v -> {
            startActivity(new Intent(this, RunActivity.class));
        });

        btnShow.setOnClickListener(v -> {
            startActivity(new Intent(this, ShowActivity.class));
        });
    }
}