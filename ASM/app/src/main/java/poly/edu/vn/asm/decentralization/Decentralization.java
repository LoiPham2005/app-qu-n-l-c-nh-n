package poly.edu.vn.asm.decentralization;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.login.LoginActivity;

public class Decentralization extends AppCompatActivity {
    Button btnPeople,btnExpert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_decentralization);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPeople = findViewById(R.id.btnPeople);
        btnExpert = findViewById(R.id.btnExpert);

        btnPeople.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btnExpert.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}