package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.BuildConfig;
import website.petrov.noue.common.model.MessageResponse;
import website.petrov.noue.common.viewmodel.BaseViewModel;
import website.petrov.noue.model.UserModel;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.LoginAPI;
import website.petrov.noue.utilities.Provider;

public final class LoginViewModel extends BaseViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public boolean isLoginAttempting = false;
    @Nullable
    private MutableLiveData<UserModel> loginModel;
    private Disposable subscription;

    @NonNull
    public MutableLiveData<UserModel> getLoginModel() {
        if (loginModel == null) {
            loginModel = new MutableLiveData<>();
        }
        return loginModel;
    }

    public void onLoginClick(@NonNull View view) {
        if (loginModel == null) {
            return;
        }
        final String value = email.getValue();
        if (TextUtils.isEmpty(value)) {
            return;
        }
        final UserModel loginUser = new UserModel(value);
        loginModel.setValue(loginUser);
    }

    public void attemptLogin(@NonNull Context context) {
        isLoginAttempting = true;

        final LoginAPI api = APIService.getInstance().create(LoginAPI.class);
        final Observable<MessageResponse> response = api.request(email.getValue(), Provider.getDeviceId(context));
        subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse.ok) {
                        Log.d("LOGIN-OK", loginResponse.message);
                    } else {
                        Log.d("LOGIN-FAIL", loginResponse.message);
                    }

                    isLoginAttempting = false;
                }, error -> {
                    Log.d("FETCH-FAIL", error.getMessage());

                    isLoginAttempting = false;

                    if (BuildConfig.DEBUG) {
                        StorageShared.setFirstRunFlag(false);

                        Activity activity = Provider.getActivity(context);
                        if (activity != null) {
                            activity.setResult(Activity.RESULT_OK);
                            activity.finish();
                        }
                    }
                });
    }

    @Override
    protected void onCleared() {
        subscription.dispose();
    }
}
