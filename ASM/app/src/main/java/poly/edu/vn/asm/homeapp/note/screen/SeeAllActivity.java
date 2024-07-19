package poly.edu.vn.asm.homeapp.note.screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.note.adapter.Note;
import poly.edu.vn.asm.homeapp.note.update.UpdateNoteActivity;

public class SeeAllActivity extends AppCompatActivity {
ImageView imgEditScreenNote,imgBackScreenNote;
Note note;
TextView tvTimeScreenNote,tvTitleScreenNote,tvContentScreenNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_all);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgEditScreenNote = findViewById(R.id.imgEditScreenNote);
        imgBackScreenNote = findViewById(R.id.imgBackScreenNote);
        tvTimeScreenNote = findViewById(R.id.tvTimeScreenNote);
        tvTitleScreenNote = findViewById(R.id.tvTitleScreenNote);
        tvContentScreenNote = findViewById(R.id.tvContentScreenNote);

        receiveData();

        imgBackScreenNote.setOnClickListener(v -> {
            onBackPressed();
        });

        imgEditScreenNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateNoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("note", note);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    private void receiveData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            note = (Note) bundle.getSerializable("note");
//            noteId = note.getId(); // Assign noteId here
            tvTitleScreenNote.setText(note.getTitle());
            tvContentScreenNote.setText(note.getContent());
            tvTimeScreenNote.setText(note.getTime());
        }
    }
}