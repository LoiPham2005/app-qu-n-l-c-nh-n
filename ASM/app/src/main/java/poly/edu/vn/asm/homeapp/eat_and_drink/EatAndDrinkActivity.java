package poly.edu.vn.asm.homeapp.eat_and_drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;

public class EatAndDrinkActivity extends AppCompatActivity {
    EditText edtSex, edtWeight, edtHeight;
    Button btnCalculate;
    TextView tvComment;
    String comment;
    String comment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eat_and_drink);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSex = findViewById(R.id.edtSex);
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        btnCalculate = findViewById(R.id.btnCalculate);

        edtSex.setOnClickListener(v -> {
            choiceSex();
        });

        btnCalculate.setOnClickListener(v -> {
            recommendation();
        });
    }

    private void choiceSex() {
        String[] items = {"Nam", "Nữ"};
        new AlertDialog.Builder(this)
                .setTitle("Chọn giới tính")
                .setSingleChoiceItems(items, -1, (dialog, which) -> {
                    edtSex.setText(items[which]);
                })
                .create().show();
    }


    private void recommendation() {
        String sex = edtSex.getText().toString();
        String weight = edtWeight.getText().toString();
        String height = edtHeight.getText().toString();
        boolean check = false;

        if (sex.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, "You have not entered enough information", Toast.LENGTH_SHORT).show();
            check = true;
        }
        if (sex.isEmpty()) {
            edtSex.setError("Please enter your sex");
            check = true;
        } else if (weight.isEmpty()) {
            edtWeight.setError("Please enter your weight");
            check = true;
        } else if (height.isEmpty()) {
            edtHeight.setError("Please enter your height");
            check = true;
        }

        if (check) {
            return;
        }

        double weight2 = Double.parseDouble(edtWeight.getText().toString());
        double height2 = Double.parseDouble(edtHeight.getText().toString());

        double caculator = weight2 / (height2 * 2);


        switch (sex) {
            case "Nữ":
                if (caculator < 1) {
                    comment = "Does not exist";
                } else if (caculator < 16) {
                    comment = "Grade 3 thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator < 17) {
                    comment = "Grade 2 Thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator < 18) {
                    comment = "Grade 1 Thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator >= 18 && caculator < 23) {
                    comment = "Normal";
                    comment2 = "You can keep a reasonable diet and exercise regimen";
                } else if (caculator >= 23 && caculator < 30) {
                    comment = "risk of being overweight";
                    comment2 = "You should avoid eating too much, fast food and should exercise every day";
                } else {
                    comment = "overweight";
                    comment2 = "You should see a doctor";
                }
                break;
            case "Nam":
                if (caculator < 1) {
                    comment = "Does not exist";
                } else if (caculator < 17) {
                    comment = "Grade 3 thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator < 18.5) {
                    comment = "Grade 2 Thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator < 20) {
                    comment = "Grade 1 Thinness";
                    comment2 = "You need to eat and exercise to improve your health";
                } else if (caculator >= 20 && caculator < 25) {
                    comment = "Normal";
                    comment2 = "You can keep a reasonable diet and exercise regimen";
                } else if (caculator >= 25 && caculator < 30) {
                    comment = "risk of being overweight";
                    comment2 = "You should avoid eating too much, fast food and should exercise every day";
                } else {
                    comment = "overweight";
                    comment2 = "You should see a doctor";
                }
                break;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.item_recomment_health, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        tvComment = view.findViewById(R.id.tvRecomment);

        builder.setTitle("Recommendations health");
        tvComment.setText(comment + "\n" + comment2);
        builder.setPositiveButton("ok", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }
}