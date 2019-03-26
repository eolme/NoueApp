package website.petrov.noue.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.databinding.ActivityProjectBinding;

public final class ProjectActivity extends BaseActivity {
    private ActivityProjectBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ProjectActivity.this, R.layout.activity_project);
        // binding.kanban
    }
}
