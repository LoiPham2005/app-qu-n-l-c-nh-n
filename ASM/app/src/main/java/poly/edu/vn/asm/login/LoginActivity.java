package poly.edu.vn.asm.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import poly.edu.vn.asm.MainActivity;
import poly.edu.vn.asm.R;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    CheckBox checkBox;
    ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if(currentUser != null){
//            // neu user da dang nhap vao tu phien truoc thi su dung user luon
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

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
        dialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        // Lấy thông tin từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedEmail = preferences.getString("email", "");
        String savedPassword = preferences.getString("password", "");

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            edtEmail.setText(savedEmail);
            edtPassword.setText(savedPassword);
        }

        // lấy thông tin từ firebase và gán vào edittext cách này không cho lấy mật khẩu
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            String email = currentUser.getEmail();
//            edtEmail.setText(email);
//            // Nếu cần, bạn có thể lấy mật khẩu từ Firebase, nhưng thường không nên lấy mật khẩu một cách rõ ràng
////             String password = "password from Firebase";
////             edtPassword.setText(password);
//        }
            btnLogin.setOnClickListener(v -> {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                boolean remember = checkBox.isChecked();

                if (remember) {
                    // Lưu dữ liệu vào SharedPreferences
                    getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
                            .putString("email", email)
                            .putString("password", password)
                            .apply();
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng nhập bằng email và password nếu đúng thông tin sẽ vào màn trong
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                // Lưu thông tin vào SharedPreferences nếu cần

                                // Chuyển sang màn hình chính
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        }
    }

