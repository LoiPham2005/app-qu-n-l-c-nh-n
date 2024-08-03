package poly.edu.vn.asm.homeapp.spirit.yoga.video;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;

public class VideoLesson1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_lesson1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        VideoView videoViewlession2 = findViewById(R.id.videoViewlession2);
        MediaController mediaController = new MediaController(this);
        videoViewlession2.setVideoPath("android.resource://" + getPackageName() +"/"+ R.raw.yoga1);

        videoViewlession2.setMediaController(mediaController);
        videoViewlession2.start();
    }
}