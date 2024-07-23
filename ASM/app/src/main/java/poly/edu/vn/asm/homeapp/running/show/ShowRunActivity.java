package poly.edu.vn.asm.homeapp.running.show;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.running.show.adapter.AdapterShowRunning;
import poly.edu.vn.asm.homeapp.running.show.adapter.Run;

public class ShowRunActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterShowRunning adapterShow;
    ArrayList<Run> listRun;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String TAG ="/////";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerViewRunShow);

        listRun = new ArrayList<>();
        LinearLayoutManager manager  =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration decoration = new DividerItemDecoration(this, manager.getOrientation());
        recyclerView.addItemDecoration(decoration);

        adapterShow = new AdapterShowRunning(this, listRun);
        recyclerView.setAdapter(adapterShow);

        getListRuns();
    }

    // Lấy dữ liệu từ firebase hiện thị vào listView
    private void getListRuns() {
        firestore.collection("runs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listRun.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Run run = document.toObject(Run.class);
                            run.setId(document.getId());
                            listRun.add(run);
                        }
                        adapterShow.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}
