package poly.edu.vn.asm.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.MainActivity;
import poly.edu.vn.asm.R;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        tvRegister = findViewById(R.id.tv_register);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.checkBox);

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String email = bundle.getString("email");
            String password = bundle.getString("password");
            edtEmail.setText(email);
            edtPassword.setText(password);
        }

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            boolean remember = checkBox.isChecked();

            if(remember){
                // lưu dữ liệu vào file Shared Preferences
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();
            }

            Toast.makeText(this, "Login succesfull", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
        });
    }
}