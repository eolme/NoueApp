package website.petrov.noue.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.utils.ContextUtils;
import website.petrov.noue.view.fragment.SettingsFragment;

public final class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        final Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPaddingRelative(0, ContextUtils.getStatusBarHeight(this), 0, 0);

        if (savedInstanceState == null) {
            final Fragment settingsFragment = new SettingsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.settings_container, settingsFragment)
                    .commit();
        }
    }
}
