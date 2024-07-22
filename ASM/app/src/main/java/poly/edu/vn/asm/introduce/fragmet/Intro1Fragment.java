package poly.edu.vn.asm.introduce.fragmet;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.login.decentralization.Decentralization;


public class Intro1Fragment extends Fragment {
    TextView tvSkip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro1, container, false);
        tvSkip = view.findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Decentralization.class));
        });

        return view;
    }
}