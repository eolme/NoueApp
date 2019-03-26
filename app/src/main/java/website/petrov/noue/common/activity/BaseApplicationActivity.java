package website.petrov.noue.common.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.utils.Constants;

public abstract class BaseApplicationActivity extends BaseActivity {
    @Constants.ApplicationState
    private int mApplicationState = Constants.STATE_VISIBLE;

    protected abstract void onApplicationVisible();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplicationState = Constants.STATE_VISIBLE;
        onApplicationVisible();
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();

        mApplicationState = Constants.STATE_VISIBLE;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (getApplicationState() == Constants.STATE_VISIBLE) {
            onApplicationVisible();
        }
    }

    @Contract(pure = true)
    @Constants.ApplicationState
    protected final int getApplicationState() {
        return mApplicationState;
    }

    private void hideApplication() {
        mApplicationState = Constants.STATE_HIDDEN;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void dispatchBackPressed() {
        dispatchBackPressed(false);
    }

    protected void dispatchBackPressed(boolean force) {
        if (force) {
            hideApplication();
        } else {
            final boolean success = moveTaskToBack(false);

            if (!success) {
                hideApplication();
            }
        }

        super.onBackPressed();
    }
}
