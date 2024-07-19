package poly.edu.vn.asm.homeapp.note.create;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.note.NoteActivity;
import poly.edu.vn.asm.homeapp.note.adapter.Note;

public class CreateNoteActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ImageView imgBack, imgSave;
    EditText edtTitle, edtContent, edtTime;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgBack = findViewById(R.id.imgBack);
        imgSave = findViewById(R.id.imgSave);
        edtTitle = findViewById(R.id.edtTitle);
        edtTime = findViewById(R.id.edtTime);
        edtContent = findViewById(R.id.edtContent);

        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        // click thêm firebase
        imgSave.setOnClickListener(v -> {
            createNote();
        });

        // click chọn thời gian
        clickTime();



        // nhận dữ liệu từ NoteActivity
//        receiveData();

    }

    private void clickTime() {
        edtTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog pickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                edtTime.setText("" + hourOfDay + ":" + minute1);
            }, hour, minute, true);
            pickerDialog.show();
        });

    }

    private void createNote() {
        imgSave.setOnClickListener(v -> {
            String id = UUID.randomUUID().toString();
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            String time = edtTime.getText().toString();
            boolean check = false;

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "You have not entered enough information", Toast.LENGTH_SHORT).show();
                check = true;

                if (title.isEmpty()) {
                    edtTitle.setError("Please enter title");
                    check = true;
                }
                if (content.isEmpty()) {
                    edtContent.setError("Please enter content");
                    check = true;
                }
            }
            if (check) {
                return;
            }

            Note note = new Note();
            note.setId(id);
            note.setTitle(title);
            note.setContent(content);
            note.setTime(time);

            firestore.collection("notes")
                    .add(note)
                    .addOnCompleteListener(command -> {
                        Log.d("zzzzzz", "addNote: " + command.isSuccessful());
                        Toast.makeText(this, "Add note successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, NoteActivity.class));
                    })
                    .addOnFailureListener(command -> {
                        Toast.makeText(this, "Add note failed", Toast.LENGTH_SHORT).show();
                    });
        });
    }



}