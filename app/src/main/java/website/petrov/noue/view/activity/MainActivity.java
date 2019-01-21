package website.petrov.noue.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseApplicationActivity;
import website.petrov.noue.databinding.ActivityMainBinding;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utilities.Constants;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.fragment.ErrorFragment;
import website.petrov.noue.view.fragment.FeedFragment;
import website.petrov.noue.view.fragment.ProjectsFragment;

public final class MainActivity extends BaseApplicationActivity implements StorageShared.UpdateListener {
    @Constants.FragmentType
    private static int mCurrentFragmentType = Constants.FRAGMENT_DEFAULT;
    private ActivityMainBinding binding;
    @Nullable
    private FeedFragment mFeedFragment;
    @Nullable
    private ProjectsFragment mProjectsFragment;

    @Override
    protected void onApplicationVisible() {
        if (StorageShared.getFirstRunFlag()) {
            showIntro();
            finish();
        } else {
            if (mCurrentFragmentType == Constants.FRAGMENT_DEFAULT) {
                setCurrentFragmentType(Constants.FRAGMENT_CARD);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        binding.navView.setCheckedItem(R.id.menu_cards);
        binding.navView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_feed: {
                            if (mCurrentFragmentType != Constants.FRAGMENT_FEED) {
                                setCurrentFragmentType(Constants.FRAGMENT_FEED);
                            }
                            break;
                        }
                        case R.id.menu_cards: {
                            if (mCurrentFragmentType != Constants.FRAGMENT_CARD) {
                                setCurrentFragmentType(Constants.FRAGMENT_CARD);
                            }
                            break;
                        }
                        case R.id.menu_settings: {
                            showSettings();
                            break;
                        }
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

        StorageShared.register(this);
    }

    @NonNull
    private Fragment getFragmentByType(@Constants.FragmentType int fragmentType) {
        switch (fragmentType) {
            case Constants.FRAGMENT_CARD: {
                if (mProjectsFragment == null) {
                    mProjectsFragment = new ProjectsFragment();
                }
                return mProjectsFragment;
            }
            case Constants.FRAGMENT_FEED: {
                if (mFeedFragment == null) {
                    mFeedFragment = new FeedFragment();
                }
                return mFeedFragment;
            }
            case Constants.FRAGMENT_DEFAULT:
            default: {
                return new ErrorFragment(getString(R.string.error_fragment));
            }
        }
    }

    private void setCurrentFragmentType(@Constants.FragmentType int fragmentType) {
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

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentFragmentType = savedInstanceState.getInt(Constants.FRAGMENT_TYPE);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawer(binding.navView);
        } else {
            dispatchBackPressed();
        }
    }

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

    public void onStorageUpdate() {
        final View mHeader = binding.navView.getHeaderView(0);
        final TextView mAccountAbout = Provider.getView(R.id.account_about, mHeader);
        final TextView mAccountName = Provider.getView(R.id.account_name, mHeader);

        final String name = StorageShared.getAccountName();
        if (name.equals(Constants.Storage.STORAGE_ACCOUNT_NAME_DEFAULT)) {
            mAccountName.setText(StorageShared.getAccountEmail());
        } else {
            mAccountName.setText(name);
        }
        mAccountAbout.setText(StorageShared.getAccountAbout());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StorageShared.unregister(this);
    }
}
