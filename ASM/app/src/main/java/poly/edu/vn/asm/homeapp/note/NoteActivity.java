package poly.edu.vn.asm.homeapp.note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.note.adapter.AdapterNote;
import poly.edu.vn.asm.homeapp.note.adapter.Note;
import poly.edu.vn.asm.homeapp.note.create.CreateNoteActivity;

public class NoteActivity extends AppCompatActivity implements AdapterNote.NoteClickListener {
    FirebaseFirestore firestore;
    ImageView imgCreate, imgDelete, imgExit;
    EditText edtSearch;
    RecyclerView recyclerView;
    AdapterNote adapterNote;
    ArrayList<Note> listNote;
    ArrayList<Note> filteredListNote;
    ProgressDialog progressDialog;
    boolean isMultiSelectMode = false;
    List<String> selectedIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        imgCreate = findViewById(R.id.imgCreate);
        imgDelete = findViewById(R.id.imgDelete);
        recyclerView = findViewById(R.id.recyclerView);
        imgExit = findViewById(R.id.imgXExit);
        edtSearch = findViewById(R.id.edtSearch);

        progressDialog = new ProgressDialog(this);

        firestore = FirebaseFirestore.getInstance();

        listNote = new ArrayList<>();
        filteredListNote = new ArrayList<>();
        adapterNote = new AdapterNote(this, filteredListNote, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterNote);

        imgCreate.setOnClickListener(v -> {
            // Chuyển sang màn hình tạo ghi chú mới
            startActivity(new Intent(this, CreateNoteActivity.class));
        });

        // Ẩn imgDelete và imgExit ban đầu
        imgDelete.setVisibility(View.GONE);
        imgExit.setVisibility(View.GONE);

        imgExit.setOnClickListener(v -> {
            // Thoát chế độ chọn nhiều
            isMultiSelectMode = false;
            selectedIds.clear();
            updateUI();
        });

        imgDelete.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete selected items?")
                        .setPositiveButton("Yes", (dialog, which) -> deleteSelectedItems())
                        .setNegativeButton("No", null)
                        .show()
        );

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fetchNotes();
    }

    private void fetchNotes() {
        progressDialog.show();
        firestore.collection("notes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listNote.clear();
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        listNote.add(new Note(
                                snapshot.getId(),
                                snapshot.getString("title"),
                                snapshot.getString("content"),
                                snapshot.getString("time")
                        ));
                    }
                    filteredListNote.clear();
                    filteredListNote.addAll(listNote);
                    runOnUiThread(() -> {
                        adapterNote.notifyDataSetChanged();
                        progressDialog.dismiss();
                    });
                })
                .addOnFailureListener(e -> runOnUiThread(() -> {
                    Toast.makeText(this, "Failed to fetch notes", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }));
    }

    private void filterNotes(String query) {
        filteredListNote.clear();
        if (query.isEmpty()) {
            filteredListNote.addAll(listNote);
        } else {
            for (Note note : listNote) {
                if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        note.getContent().toLowerCase().contains(query.toLowerCase())) {
                    filteredListNote.add(note);
                }
            }
        }
        runOnUiThread(() -> adapterNote.notifyDataSetChanged());
    }

    @Override
    public void onItemLongClicked(int position) {
        if (!isMultiSelectMode) {
            isMultiSelectMode = true;
            selectedIds.clear(); // Xóa danh sách mục được chọn trước đó
        }
        toggleItemSelected(position);
        updateUI();
    }

    @Override
    public void onItemClicked(int position) {
        if (isMultiSelectMode) {
            toggleItemSelected(position);
            updateUI();
        }
    }

    private void toggleItemSelected(int position) {
        String itemId = filteredListNote.get(position).getId();
        if (selectedIds.contains(itemId)) {
            selectedIds.remove(itemId);
        } else {
            selectedIds.add(itemId);
        }
    }

    private void deleteSelectedItems() {
        if (selectedIds.isEmpty()) {
            Toast.makeText(this, "Select items to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        for (String itemId : selectedIds) {
            firestore.collection("notes").document(itemId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Sau khi xóa thành công, xóa mục khỏi danh sách hiển thị
                        Note noteToRemove = null;
                        for (Note note : listNote) {
                            if (note.getId().equals(itemId)) {
                                noteToRemove = note;
                                break;
                            }
                        }
                        if (noteToRemove != null) {
                            listNote.remove(noteToRemove);
                        }
                        if (selectedIds.isEmpty()) {
                            fetchNotes(); // Load lại danh sách ghi chú sau khi xóa xong
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
                    });
        }

        selectedIds.clear();
        isMultiSelectMode = false;
        updateUI();
        progressDialog.dismiss();
    }

    private void updateUI() {
        adapterNote.setSelectedItems(selectedIds);
        adapterNote.setMultiSelectMode(isMultiSelectMode);
        adapterNote.notifyDataSetChanged();

        // Hiển thị hoặc ẩn imgDelete và imgExit tùy vào chế độ chọn nhiều
        imgDelete.setVisibility(isMultiSelectMode && !selectedIds.isEmpty() ? View.VISIBLE : View.GONE);
        imgExit.setVisibility(isMultiSelectMode ? View.VISIBLE : View.GONE);
    }
}
