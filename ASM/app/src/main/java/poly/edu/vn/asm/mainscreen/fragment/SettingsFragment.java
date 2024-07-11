package poly.edu.vn.asm.mainscreen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.settingscreen.Setting;
import poly.edu.vn.asm.settingscreen.adapter.AdapterSettings;


public class SettingsFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterSettings adapterSettings;
    ArrayList<Setting> listSettings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        // thiết lập layout cho recyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        // thêm gạch chân cho recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // list item
        listObject();
        // truyền dữ liệu vào adapter
        adapterSettings = new AdapterSettings(getContext(), listSettings);
        recyclerView.setAdapter(adapterSettings);

        return view;
    }

    private void listObject() {
        listSettings.add(new Setting("Personal Information", R.drawable.user));
        listSettings.add(new Setting("Change Password", R.drawable.password));
        listSettings.add(new Setting("Support Hotline", R.drawable.hotline));
        listSettings.add(new Setting("User Manual",  R.drawable.user_manual));
        listSettings.add(new Setting("Frequently Asked Questions", R.drawable.questions));
    }
}