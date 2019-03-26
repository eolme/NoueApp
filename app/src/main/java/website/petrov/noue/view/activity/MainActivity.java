package website.petrov.noue.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseApplicationActivity;
import website.petrov.noue.databinding.ActivityMainBinding;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utils.Constants;
import website.petrov.noue.utils.DebugUtils;
import website.petrov.noue.view.fragment.ErrorFragment;
import website.petrov.noue.view.fragment.FeedFragment;
import website.petrov.noue.view.fragment.ProjectsFragment;

public final class MainActivity extends BaseApplicationActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Constants.FragmentType
    private int mCurrentFragmentType;
    private ActivityMainBinding binding;
    @Nullable
    private FeedFragment mFeedFragment;
    @Nullable
    private ProjectsFragment mProjectsFragment;

    @Override
    protected void onApplicationVisible() {
        if (StorageShared.getFirstRunFlag()) {
            showIntro();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String action = getIntent().getAction();

        if (Constants.ACTION_FEED.equals(action)) {
            mCurrentFragmentType = Constants.FRAGMENT_FEED;
        }

        if (Constants.ACTION_PROJECTS.equals(action)) {
            mCurrentFragmentType = Constants.FRAGMENT_PROJECTS;
        }

        if (mCurrentFragmentType == 0) {
            if (savedInstanceState == null) {
                mCurrentFragmentType = Constants.FRAGMENT_PROJECTS;
            } else {
                mCurrentFragmentType =
                        savedInstanceState.getInt(Constants.FRAGMENT_TYPE, Constants.FRAGMENT_PROJECTS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (binding == null) {
            binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        }

        setCurrentFragmentType(mCurrentFragmentType);

        switch (mCurrentFragmentType) {
            case Constants.FRAGMENT_FEED:
                binding.navView.setCheckedItem(R.id.menu_feed);
                break;

            case Constants.FRAGMENT_PROJECTS:
                binding.navView.setCheckedItem(R.id.menu_projects);
                break;

            default:
                DebugUtils.log("Undefined behavior for fragment state");
                break;
        }

        binding.navView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_feed:
                            if (mCurrentFragmentType != Constants.FRAGMENT_FEED) {
                                setCurrentFragmentType(Constants.FRAGMENT_FEED);
                            }
                            break;

                        case R.id.menu_projects:
                            if (mCurrentFragmentType != Constants.FRAGMENT_PROJECTS) {
                                setCurrentFragmentType(Constants.FRAGMENT_PROJECTS);
                            }
                            break;

                        case R.id.menu_settings:
                            showSettings();
                            break;

                        case R.id.menu_help:
                            break;

                        default:
                            break;
                    }

                    if (menuItem.isCheckable()) {
                        binding.drawerLayout.closeDrawers();
                        return true;
                    } else {
                        return false;
                    }
                }
        );

        onStorageUpdate();

        StorageShared.getStorageInstance(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        StorageShared.getStorageInstance(this)
                .unregisterOnSharedPreferenceChangeListener(this);

        super.onPause();
    }

    @NonNull
    private Fragment getFragmentByType(@Constants.FragmentType int fragmentType) {
        switch (fragmentType) {
            case Constants.FRAGMENT_PROJECTS:
                if (mProjectsFragment == null) {
                    mProjectsFragment = new ProjectsFragment();
                }
                return mProjectsFragment;

            case Constants.FRAGMENT_FEED:
                if (mFeedFragment == null) {
                    mFeedFragment = new FeedFragment();
                }
                return mFeedFragment;

            default:
                return new ErrorFragment(getString(R.string.error_fragment));
        }
    }

    private void setCurrentFragmentType(@Constants.FragmentType int fragmentType) {
        mCurrentFragmentType = fragmentType;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, getFragmentByType(fragmentType))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.FRAGMENT_TYPE, mCurrentFragmentType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentFragmentType = savedInstanceState.getInt(Constants.FRAGMENT_TYPE, Constants.FRAGMENT_PROJECTS);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawer(binding.navView);
        } else {
            dispatchBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                binding.drawerLayout.closeDrawer(binding.navView);
            } else {
                binding.drawerLayout.openDrawer(binding.navView);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void showIntro() {
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
    }

    public void showSettings() {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    private void onStorageUpdate() {
        if (binding == null) {
            return; // Activity not yet initialized
        }

        final View mHeader = binding.navView.getHeaderView(0);
        final TextView mAccountAbout = ViewCompat.requireViewById(mHeader, R.id.account_about);
        final TextView mAccountName = ViewCompat.requireViewById(mHeader, R.id.account_name);

        final String name = StorageShared.getAccountName();
        if (name.equals(Constants.Storage.STORAGE_ACCOUNT_NAME_DEFAULT)) {
            mAccountName.setText(StorageShared.getAccountEmail());
        } else {
            mAccountName.setText(name);
        }
        mAccountAbout.setText(StorageShared.getAccountAbout());
    }

    @Override
    public void onSharedPreferenceChanged(@Nullable SharedPreferences sharedPreferences, @NonNull String key) {
        onStorageUpdate();
    }
}
