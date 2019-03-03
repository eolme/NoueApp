package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.model.UserModel;
import website.petrov.noue.model.request.LoginRequest;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.api.LoginAPI;
import website.petrov.noue.utilities.Provider;

import static website.petrov.noue.utilities.Constants.API.METHOD_LOGIN;

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

    @SuppressWarnings("unchecked")
    public void attemptLogin(@NonNull Context context) {
        isLoginAttempting = true;

        final LoginAPI api = APIService.getInstance().create(LoginAPI.class);
        final Request request = new RequestBuilder()
                .withMethod(METHOD_LOGIN)
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
                        Log.d("RESULT", loginResponse.result.getClass().toString());

                        if (loginResponse.result instanceof LinkedTreeMap) {
                            final LinkedTreeMap<Object, Object> result = (LinkedTreeMap<Object, Object>) loginResponse.result;
                            StorageShared.setAccountName((String) result.get("name"));
                            StorageShared.setAccountAbout((String) result.get("about"));
                            StorageShared.setAccountEmail((String) result.get("name"));
                        }

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
        if (subscription != null) {
            subscription.dispose();
        }
    }
}
