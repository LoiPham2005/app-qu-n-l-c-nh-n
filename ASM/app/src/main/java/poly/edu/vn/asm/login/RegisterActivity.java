package poly.edu.vn.asm.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poly.edu.vn.asm.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtName = findViewById(R.id.usernameSignUp);
        edtEmail = findViewById(R.id.emailSignUp);
        edtPassword = findViewById(R.id.passwordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtConfirmPassword = findViewById(R.id.passwordSignUp2);

        btnSignUp.setOnClickListener(v -> {
            String username = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String confirmPassword = edtConfirmPassword.getText().toString();
            boolean check = false;

            if (username.isEmpty()) {
                edtName.setError("Please enter your username");
                check = true;
            } else if (email.isEmpty()) {
                edtEmail.setError("Please enter your email");
                check = true;
            } else if (password.isEmpty()) {
                edtPassword.setError("Please enter your password");
                check = true;
            }

            if (username.length() < 5) {
                edtName.setError("Username must be at least 5 characters");
                check = true;
            }

            if (email.length() < 5) {
                edtEmail.setError("Email must be at least 5 characters");
                check = true;
            } else if (!email.contains("@gmail.com")) {
                edtEmail.setError("Email must be @gmail.com");
                check = true;
            }
            if (password.length() < 6) {
                edtPassword.setError("Password must be at least 6 characters");
                check = true;
            } else if (!isPasswordValid(password)) {
                edtPassword.setError("Password must contain at least one uppercase letter, one special character, and be at least 6 characters long");
                check = true;
            }
            if (!password.equals(confirmPassword)) {
                edtConfirmPassword.setError("Password does not match");
                check = true;
            }

            if (check) {
                return;
            }

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        getSharedPreferences("data", MODE_PRIVATE).edit()
                                .putString("username", username)
                                .putString("email", email)
                                .putString("password", password)
                                .apply();

                        Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_SHORT).show();

                        // Chuyển đến LoginActivity và gửi dữ liệu
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("email1", email);
                        intent.putExtra("password1", password);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

    }

    private boolean isPasswordValid(String password) {
        // Biểu thức chính quy kiểm tra ít nhất 1 ký tự đặc biệt và 1 ký tự viết hoa
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
