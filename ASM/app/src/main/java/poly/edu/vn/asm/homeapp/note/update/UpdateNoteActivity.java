package poly.edu.vn.asm.homeapp.note.update;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.note.NoteActivity;
import poly.edu.vn.asm.homeapp.note.adapter.Note;

public class UpdateNoteActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ImageView imgBack, imgSave;
    EditText edtTitle, edtContent, edtTime;
    ProgressDialog progressDialog;
    String noteId;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgBack = findViewById(R.id.imgBackUpdate);
        imgSave = findViewById(R.id.imgSaveUpdate);
        edtTitle = findViewById(R.id.edtTitleUpdate);
        edtTime = findViewById(R.id.edtTimeUpdate);
        edtContent = findViewById(R.id.edtContentUpdate);

        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        // Receive data from previous activity
        receiveData();

        // Click to choose time
        clickTime();

        // Click to update note
        imgSave.setOnClickListener(v -> {
            updateNote();
        });
    }

    private void receiveData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            note = (Note) bundle.getSerializable("note");
            noteId = note.getId(); // Assign noteId here
            edtTitle.setText(note.getTitle());
            edtContent.setText(note.getContent());
            edtTime.setText(note.getTime());
        }
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

    private void updateNote() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String time = edtTime.getText().toString();

        if (title.isEmpty() || content.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(note.getContent().equals(content) && note.getTitle().equals(title) && note.getTime().equals(time)){
            Toast.makeText(this, "No changes", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Updating note...");
        progressDialog.show();

        firestore.collection("notes").document(noteId)
                .update("title", title, "content", content , "time", time)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, NoteActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });
    }
}
