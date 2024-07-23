package poly.edu.vn.asm.homeapp.running.run;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeapp.running.show.adapter.Run;

public class RunActivity extends AppCompatActivity implements SensorEventListener {
    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 1;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private TextView tvStepCount;
    private Button btnStart, btnReset, btnSaveRun;
    private boolean isCounting = false;
    private int stepCount = 0;
    private int initialStepCount = 0;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_run);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvStepCount = findViewById(R.id.tvStepCount);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnSaveRun = findViewById(R.id.btnSaveRun);
        db = FirebaseFirestore.getInstance();

        // Kiểm tra quyền ACTIVITY_RECOGNITION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    ACTIVITY_RECOGNITION_REQUEST_CODE);
        } else {
            initStepCounter();
        }

        btnStart.setOnClickListener(v -> {
            if (!isCounting) {
                isCounting = true;
                btnStart.setText("Stop");
                initialStepCount = stepCount;
            } else {
                isCounting = false;
                btnStart.setText("Start");
            }
        });

        btnReset.setOnClickListener(v -> {
            stepCount = 0;
            initialStepCount = 0;
            tvStepCount.setText("Steps: 0");
        });

        btnSaveRun.setOnClickListener(v -> saveRunData());
    }

    private void initStepCounter() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (stepCounterSensor != null) {
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    private void saveRunData() {
        String id = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();  // lấy thời gian hiện tại
        Run runData = new Run(id, stepCount, timestamp);

        db.collection("runs")
                .add(runData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("RunActivity", "Run data saved with ID: " + documentReference.getId());
                    Toast.makeText(RunActivity.this, "Run data saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("RunActivity", "Error saving run data", e);
                    Toast.makeText(RunActivity.this, "Error saving run data", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (isCounting) {
                stepCount = (int) event.values[0] - initialStepCount;
                tvStepCount.setText("Steps: " + stepCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Không cần làm gì
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null && stepCounterSensor != null) {
            sensorManager.registerListener((SensorEventListener) this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(RunActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTIVITY_RECOGNITION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initStepCounter();
            } else {
                tvStepCount.setText("Permission denied");
            }
        }
    }
}
