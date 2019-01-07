package website.petrov.noue.view.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.fragment.SettingsFragment;

public final class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Toolbar toolbar = Provider.getView(R.id.settings_toolbar, this);
        setSupportActionBar(toolbar);
        toolbar.setPaddingRelative(0, Provider.getStatusBarHeight(this), 0, 0);

        if (savedInstanceState == null) {
            final Fragment preferenceFragment = new SettingsFragment();
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.settings_container, preferenceFragment);
            ft.commit();
        }
    }
}
