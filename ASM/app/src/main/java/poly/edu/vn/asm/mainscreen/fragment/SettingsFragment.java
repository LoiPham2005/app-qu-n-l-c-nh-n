package poly.edu.vn.asm.mainscreen.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import poly.edu.vn.asm.R;
import poly.edu.vn.asm.login.decentralization.Decentralization;
import poly.edu.vn.asm.settingscreen.Setting;
import poly.edu.vn.asm.settingscreen.adapter.AdapterSettings;

public class SettingsFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String IMAGE_URI_KEY = "imageUri";
    RecyclerView recyclerView;
    AdapterSettings adapterSettings;
    ArrayList<Setting> listSettings = new ArrayList<>();
    ImageView imgEdit, imgAvatar;
    Uri imageUri;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgEdit = view.findViewById(R.id.imgEdit);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Decentralization.class));
        });
        // Khởi tạo SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // Thiết lập click listener cho imgEdit
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Thiết lập layout cho recyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        // Thêm gạch chân cho recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Lấy ảnh đã lưu từ SharedPreferences và hiển thị lên imgAvatar nếu có
        loadAvatarFromSharedPreferences();

        // List item
        listObject();

        // Truyền dữ liệu vào adapter
        adapterSettings = new AdapterSettings(getContext(), listSettings);
        recyclerView.setAdapter(adapterSettings);

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgAvatar.setImageURI(imageUri); // Hiển thị ảnh đã chọn
            saveImageUriToSharedPreferences(imageUri.toString()); // Lưu đường dẫn ảnh vào SharedPreferences
        }
    }

    private void saveImageUriToSharedPreferences(String imageUri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_URI_KEY, imageUri);
        editor.apply();
    }

    private void loadAvatarFromSharedPreferences() {
        String savedImageUri = sharedPreferences.getString(IMAGE_URI_KEY, null);
        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
            imgAvatar.setImageURI(imageUri);
        }
    }

    private void listObject() {
        listSettings.add(new Setting("Personal Information", R.drawable.user));
        listSettings.add(new Setting("Change Password", R.drawable.password));
        listSettings.add(new Setting("Support Hotline", R.drawable.hotline));
        listSettings.add(new Setting("User Manual", R.drawable.user_manual));
        listSettings.add(new Setting("Frequently Asked Questions", R.drawable.questions));
    }
}
