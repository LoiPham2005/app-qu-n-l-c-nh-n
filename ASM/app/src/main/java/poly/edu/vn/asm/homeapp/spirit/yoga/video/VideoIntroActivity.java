package poly.edu.vn.asm.homeapp.spirit.yoga.video;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.asm.R;

public class VideoIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MediaController mediaController = new MediaController(this);
//        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.yoga1));

        VideoView videoView = findViewById(R.id.VVIntro);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}