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
import website.petrov.noue.utilities.OS;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        OS.setTransparentStatusBar(this);

        binding.navView.setCheckedItem(R.id.menu_cards);
        binding.navView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_feed: {
                            if (mCurrentFragmentType != Constants.FRAGMENT_FEED) {
                                setCurrentFragmentType(Constants.FRAGMENT_FEED);
                            }
                            setCurrentFragment();
                            break;
                        }
                        case R.id.menu_cards: {
                            if (mCurrentFragmentType != Constants.FRAGMENT_CARD) {
                                setCurrentFragmentType(Constants.FRAGMENT_CARD);
                            }
                            setCurrentFragment();
                            break;
                        }
                        case R.id.menu_settings: {
                            showSettings();
                            break;
                        }
                    }

                    if (binding.drawerLayout != null) {
                        binding.drawerLayout.closeDrawers();
                    }

                    return menuItem.isCheckable();
                }
        );

        if (mCurrentFragmentType == Constants.FRAGMENT_DEFAULT) {
            setCurrentFragmentType(Constants.FRAGMENT_CARD);
        }

        setCurrentFragment();
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
                return new ErrorFragment("Упс! Что-то пошло не так...");
            }
        }
    }

    private void setCurrentFragmentType(@Constants.FragmentType int fragmentType) {
        mCurrentFragmentType = fragmentType;
    }

    private void setCurrentFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, getFragmentByType(mCurrentFragmentType))
                .addToBackStack(null)
                .commit();
    }

    private void showFirstrunIfNecessary() {
        if (StorageShared.getFirstRunFlag()) {
            startActivityForResult(
                    new Intent(MainActivity.this, IntroActivity.class),
                    Constants.REQUEST_FIRST_RUN);
        }
    }

    @Override
    protected void onApplicationVisible() {
        showFirstrunIfNecessary();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_FIRST_RUN) {
            if (resultCode == RESULT_OK) {
                StorageShared.setFirstRunFlag(false);
            } else {
                onBackPressed();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
    protected void onStop() {
        super.onStop();
        StorageShared.unregister(this);
    }
}
