package poly.edu.vn.asm.homeapp.running.show.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import poly.edu.vn.asm.R;

public class AdapterShowRunning extends RecyclerView.Adapter<AdapterShowRunning.showRunning> {
    Context context;
    ArrayList<Run> runArrayList;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public AdapterShowRunning(Context context, ArrayList<Run> runArrayList) {
        this.context = context;
        this.runArrayList = runArrayList;
    }

    @NonNull
    @Override
    public showRunning onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_show_running, null);
        return new showRunning(view);
    }

    @Override
    public void onBindViewHolder(@NonNull showRunning holder, int position) {
        Run run = runArrayList.get(position);
        holder.tvStep.setText(String.valueOf(run.getStep()));

        // Format and display timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(run.getTimestamp()));
        holder.tvTimestamp.setText(formattedDate);

        // xoá thông tin
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("confirm delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        firestore.collection("runs").document(run.getId())
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    runArrayList.remove(position);
                                    notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (runArrayList != null) {
            return runArrayList.size();
        }
        return 0;
    }

    public static class showRunning extends RecyclerView.ViewHolder {
        TextView tvStep;
        TextView tvTimestamp;
        public showRunning(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.stepRunning);
            tvTimestamp = itemView.findViewById(R.id.timestampRunning);
        }
    }
}
