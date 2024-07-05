package poly.edu.vn.asm.mainscreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import poly.edu.vn.asm.mainscreen.fragment.AlarmFragment;
import poly.edu.vn.asm.mainscreen.fragment.ChatsFragment;
import poly.edu.vn.asm.mainscreen.fragment.HomeFragment;
import poly.edu.vn.asm.mainscreen.fragment.SettingsFragment;

public class EffectMain extends FragmentStateAdapter {

    public EffectMain(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ChatsFragment();
            case 2:
                return new AlarmFragment();
            case 3:
                return new SettingsFragment();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
