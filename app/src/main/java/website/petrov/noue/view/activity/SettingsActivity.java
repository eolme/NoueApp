package website.petrov.noue.view.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.fragment.SettingsFragment;

public final class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPaddingRelative(0, Provider.getStatusBarHeight(this), 0, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }
}
