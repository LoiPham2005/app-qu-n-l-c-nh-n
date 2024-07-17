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
import poly.edu.vn.asm.chats.Friends;
import poly.edu.vn.asm.chats.adapter.AdapterFriends;


public class ChatsFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterFriends adapterFriends;
    ArrayList<Friends> listFriends = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.rvChats);

        listFriends.add(new Friends("Lợi Phạm", "ok",R.drawable.ic_launcher_background));
        listFriends.add(new Friends("Thành Phạm", "ok",R.drawable.ic_launcher_background));
        listFriends.add(new Friends("Hương Nguyễn", "ok",R.drawable.ic_launcher_background));
        listFriends.add(new Friends("Phong Lê", "ok",R.drawable.ic_launcher_background));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapterFriends = new AdapterFriends(getActivity(), listFriends);

        // thêm gạch chân cho recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterFriends);
        return view;
    }
}