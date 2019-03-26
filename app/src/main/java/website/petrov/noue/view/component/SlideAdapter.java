package website.petrov.noue.view.component;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.Model;
import website.petrov.noue.view.fragment.SlideFragment;

public final class SlideAdapter extends FragmentStatePagerAdapter {
    @NonNull
    private final List<Model> mSlides;

    public SlideAdapter(@NonNull FragmentManager fm, @NonNull List<Model> slides) {
        super(fm);
        mSlides = slides;
    }

    @Contract("_ -> new")
    @Override
    @NonNull
    public Fragment getItem(int position) {
        return new SlideFragment(mSlides.get(position % getRealCount()));
    }

    @Contract(pure = true)
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Contract(pure = true)
    int getRealCount() {
        return mSlides.size();
    }
}
