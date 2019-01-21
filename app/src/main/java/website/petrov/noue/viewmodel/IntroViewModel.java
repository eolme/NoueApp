package website.petrov.noue.viewmodel;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import website.petrov.noue.common.viewmodel.BaseViewModel;
import website.petrov.noue.utilities.Constants;
import website.petrov.noue.view.activity.LoginActivity;

import static website.petrov.noue.utilities.Provider.getCompatActivity;

public final class IntroViewModel extends BaseViewModel {
    public void startLogin(@NonNull View view) {
        view.setEnabled(false);
        AppCompatActivity activity = getCompatActivity(view.getContext());
        if (activity == null) {
            return;
        }
        activity.startActivityForResult(
                new Intent(activity, LoginActivity.class), Constants.REQUEST_SUCCESS_LOGIN);
    }
}
