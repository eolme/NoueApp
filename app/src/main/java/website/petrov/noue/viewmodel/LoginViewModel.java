package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.common.viewmodel.BaseViewModel;
import website.petrov.noue.model.LoginRequest;
import website.petrov.noue.model.UserModel;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.login.LoginAPI;
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
        final Request request = new RequestBuilder()
                .withParams(new Object[]{
                        new LoginRequest(Provider.getDeviceId(context),
                                StorageShared.getInstanceId(),
                                Objects.requireNonNull(email.getValue()))
                })
                .build();
        final Observable<Response> response = api.attempt(request);
        subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    final Activity activity = Provider.getActivity(context);
                    if (activity == null) {
                        return;
                    }

                    if (loginResponse.result != null) {
                        Log.d("LOGIN-OK", loginResponse.result.toString());

                        final UserModel user = (UserModel) loginResponse.result;
                        StorageShared.setAccountName(user.getName());
                        StorageShared.setAccountAbout(user.getAbout());
                        StorageShared.setAccountEmail(user.getEmail());

                        activity.setResult(Activity.RESULT_OK);
                    } else {
                        Log.d("LOGIN-FAIL", loginResponse.error.message);

                        activity.setResult(Activity.RESULT_CANCELED);
                    }

                    isLoginAttempting = false;

                    activity.finish();
                }, error -> {
                    Log.d("FETCH-FAIL", error.getMessage());

                    isLoginAttempting = false;

                    final Activity activity = Provider.getActivity(context);
                    if (activity != null) {
                        activity.finish();
                    }
                });
    }

    @Override
    protected void onCleared() {
        subscription.dispose();
    }
}
