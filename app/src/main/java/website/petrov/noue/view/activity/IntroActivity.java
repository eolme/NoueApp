package website.petrov.noue.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseApplicationActivity;
import website.petrov.noue.common.animation.FadePageTransformer;
import website.petrov.noue.databinding.ActivityIntroBinding;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utilities.Constants;
import website.petrov.noue.view.component.SlideAdapter;
import website.petrov.noue.viewmodel.IntroViewModel;
import website.petrov.noue.viewmodel.SlideViewModel;

public final class IntroActivity extends BaseApplicationActivity {
    private ActivityIntroBinding binding;

    @Override
    protected void onApplicationVisible() {
        if (binding != null) {
            binding.introViewpager.setCurrentItem(0);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SlideViewModel slideViewModel = new SlideViewModel(getApplication());

        binding = DataBindingUtil.setContentView(IntroActivity.this, R.layout.activity_intro);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new IntroViewModel());
        binding.introViewpager.setAdapter(new SlideAdapter(getSupportFragmentManager(), slideViewModel.getData()));
        binding.introViewpager.setPageTransformer(false, new FadePageTransformer());
    }

    @Override
    public void onBackPressed() {
        final int current = binding.introViewpager.getCurrentItem();
        if (current == 0) {
            dispatchBackPressed();
        } else {
            binding.introViewpager.setCurrentItem(current - 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_SUCCESS_LOGIN) {
            StorageShared.setFirstRunFlag(resultCode != RESULT_OK);
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}