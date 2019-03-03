package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import website.petrov.noue.utilities.Constants;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.activity.LoginActivity;

public final class IntroViewModel extends ViewModel {
    public void startLogin(@NonNull View view) {
        view.setEnabled(false);
        final Activity activity = Provider.getActivity(view.getContext());
        if (activity == null) {
            return;
        }
        activity.startActivityForResult(
                new Intent(activity, LoginActivity.class), Constants.REQUEST_SUCCESS_LOGIN);
    }
}
