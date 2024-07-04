package poly.edu.vn.asm.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;

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

            if(username.isEmpty()){
                edtName.setError("Please enter your username");
                check = true;
            } else if(email.isEmpty()){
                edtEmail.setError("Please enter your email");
                check = true;
            } else if(password.isEmpty()){
                edtPassword.setError("Please enter your password");
                check = true;
            }

            if(username.length()<5){
                edtName.setError("Username must be at least 5 characters");
                check = true;
            }

            if(email.length()<5){
                edtEmail.setError("Email must be at least 5 characters");
                check = true;
            }else if(!email.contains("@gmail.com")){
                edtEmail.setError("Email must be @gmail.com");
                check = true;
            }
            if(password.length()<6){
                edtPassword.setError("Password must be at least 6 characters");
                check = true;
            }
            if(!password.equals(confirmPassword)){
                edtConfirmPassword.setError("Password does not match");
                check = true;
            }

            if(check){
                return;
            }

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });


    }
}