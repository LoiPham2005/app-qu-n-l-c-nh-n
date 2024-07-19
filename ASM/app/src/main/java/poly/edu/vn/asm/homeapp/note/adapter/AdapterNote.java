package poly.edu.vn.asm.homeapp.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.note.screen.SeeAllActivity;
import poly.edu.vn.asm.homeapp.note.update.UpdateNoteActivity;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.NoteViewHolder> {
    private Context context;
    private ArrayList<Note> listNote;
    private boolean isMultiSelectMode = false;
    private List<String> selectedIds = new ArrayList<>();
    private NoteClickListener noteClickListener;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public AdapterNote(Context context, ArrayList<Note> listNote, NoteClickListener noteClickListener) {
        this.context = context;
        this.listNote = listNote;
        this.noteClickListener = noteClickListener;
    }

    public void setMultiSelectMode(boolean multiSelectMode) {
        isMultiSelectMode = multiSelectMode;
    }

    public void setSelectedItems(List<String> selectedIds) {
        this.selectedIds = selectedIds;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = listNote.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.tvTime.setText(note.getTime());

        holder.checkBox.setVisibility(isMultiSelectMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(selectedIds.contains(note.getId()));

        holder.itemView.setOnLongClickListener(v -> {
            noteClickListener.onItemLongClicked(position);
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            if (isMultiSelectMode) {
                noteClickListener.onItemClicked(position);
            } else {
                Intent intent = new Intent(context, SeeAllActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.imgMenuVertical.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.imgMenuVertical);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_vertical_note, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_Delete) {
                    new AlertDialog.Builder(context)
                            .setTitle("Confirm Delete")
                            .setMessage("Are you sure you want to delete this note?")
                            .setPositiveButton("Yes", (dialog, which) ->
                                    firestore.collection("notes").document(note.getId())
                                            .delete()
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                listNote.remove(position);
                                                notifyDataSetChanged();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
                                            })
                            )
                            .setNegativeButton("No", null)
                            .create()
                            .show();

                } else if (id == R.id.menu_Edit) {
                    Intent intent = new Intent(context, UpdateNoteActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note", note);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvTime;
        CheckBox checkBox;
        ImageView imgMenuVertical;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleNote);
            tvContent = itemView.findViewById(R.id.tvContentNote);
            tvTime = itemView.findViewById(R.id.tvTimeNote);
            checkBox = itemView.findViewById(R.id.checkBox);
            imgMenuVertical = itemView.findViewById(R.id.imgMenuVertical);

            checkBox.setOnClickListener(v -> {
                // Notify adapter that an item has been clicked to update the selection
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    itemView.performClick();
                }
            });
        }
    }

    public interface NoteClickListener {
        void onItemLongClicked(int position);

        void onItemClicked(int position);
    }
}
