package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.internal.LinkedTreeMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.model.UserModel;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.api.LoginAPI;
import website.petrov.noue.utils.ContextUtils;
import website.petrov.noue.utils.DebugUtils;

import static website.petrov.noue.utils.Constants.API.METHOD_LOGIN;

public final class LoginViewModel extends ViewModel {
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
                .withMethod(METHOD_LOGIN)
                .withParams(new Object[]{
                        ContextUtils.getDeviceId(context),
                        StorageShared.getInstanceId(),
                        email.getValue()
                })
                .build();
        final Observable<Response> response = api.attempt(request);
        subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper(), true))
                .subscribe(loginResponse -> {
                    final Activity activity = ContextUtils.getActivity(context);
                    if (activity == null) {
                        return;
                    }

                    if (loginResponse.result instanceof LinkedTreeMap) {
                        DebugUtils.log("Login is ok", loginResponse);

                        //noinspection unchecked
                        final LinkedTreeMap<Object, Object> result = (LinkedTreeMap<Object, Object>) loginResponse.result;

                        StorageShared.setAccountName((String) result.get("name"));
                        StorageShared.setAccountAbout((String) result.get("about"));
                        StorageShared.setAccountEmail((String) result.get("name"));

                        activity.setResult(Activity.RESULT_OK);
                    } else {
                        DebugUtils.log("Login is failed", loginResponse);

                        activity.setResult(Activity.RESULT_CANCELED);
                    }

                    isLoginAttempting = false;

                    activity.finish();
                }, error -> {
                    DebugUtils.log("Fetch failed", error);

                    isLoginAttempting = false;

                    // TODO: Show dialog
                });
    }

    @Override
    protected void onCleared() {
        if (subscription != null) {
            subscription.dispose();
        }
    }
}
