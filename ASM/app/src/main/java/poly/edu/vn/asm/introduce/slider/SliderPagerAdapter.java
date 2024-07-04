package poly.edu.vn.asm.introduce.slider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import poly.edu.vn.asm.introduce.fragmet.Intro1Fragment;
import poly.edu.vn.asm.introduce.fragmet.Intro2Fragment;
import poly.edu.vn.asm.introduce.fragmet.Intro3Fragment;

public class SliderPagerAdapter extends FragmentStateAdapter {
    public static String[] tabTitles = {".", ".", "."};

    public SliderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Intro1Fragment();
            case 1:
                return new Intro2Fragment();
            case 2:
                return new Intro3Fragment();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return tabTitles.length;
    }
}
