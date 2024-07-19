package poly.edu.vn.asm.introduce.fragmet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import poly.edu.vn.asm.MainActivity;
import poly.edu.vn.asm.R;
import poly.edu.vn.asm.decentralization.Decentralization;
import poly.edu.vn.asm.login.LoginActivity;


public class Intro3Fragment extends Fragment {
    Button btnGetStarted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro3, container, false);
        btnGetStarted = view.findViewById(R.id.btn_next);
        btnGetStarted.setOnClickListener(v -> {
            if(getActivity() != null){
                startActivity(new Intent(getActivity(), Decentralization.class));
            }
        });

        return view;

    }
}