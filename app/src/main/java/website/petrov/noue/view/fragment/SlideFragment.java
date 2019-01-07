package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.databinding.FragmentSlideBinding;
import website.petrov.noue.model.SlideModel;

public final class SlideFragment extends Fragment {
    @NonNull
    private final SlideModel mSlide;

    public SlideFragment(@NonNull SlideModel title) {
        mSlide = title;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final FragmentSlideBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_slide, container, false);
        binding.setSlide(mSlide);
        return binding.getRoot();
    }
}
