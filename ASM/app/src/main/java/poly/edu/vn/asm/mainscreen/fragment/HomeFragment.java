package poly.edu.vn.asm.mainscreen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeadapter.AdapterHome;
import poly.edu.vn.asm.homeadapter.model.HomeModel;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterHome adapterHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Thiết lập RecyclerView
        recyclerView = view.findViewById(R.id.rv_home);
        adapterHome = new AdapterHome(getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapterHome.setData(getListUser());
        recyclerView.setAdapter(adapterHome);

        return view;
    }

    private ArrayList<HomeModel> getListUser() {
        ArrayList<HomeModel> list = new ArrayList<>();

        list.add(new HomeModel(R.drawable.all, "Note"));
        list.add(new HomeModel(R.drawable.running, "Runing"));

        list.add(new HomeModel(R.drawable.spirit, "Spirit"));
        list.add(new HomeModel(R.drawable.slipping, "Slipping"));

        list.add(new HomeModel(R.drawable.food, "Food"));
        list.add(new HomeModel(R.drawable.health, "Health"));

        list.add(new HomeModel(R.drawable.counselor, "Counselor"));
        list.add(new HomeModel(R.drawable.friends, "Friends"));

        return list;
    }
}