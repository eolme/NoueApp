package website.petrov.noue.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import website.petrov.noue.common.viewmodel.BaseViewModel;
import website.petrov.noue.model.LoginModel;

public final class LoginViewModel extends BaseViewModel {
    @NonNull
    public MutableLiveData<String> email = new MutableLiveData<>();
    @Nullable
    private MutableLiveData<LoginModel> loginModel;

    @NonNull
    public MutableLiveData<LoginModel> getLoginModel() {
        if (loginModel == null) {
            loginModel = new MutableLiveData<>();
        }
        return loginModel;
    }

    public void onLoginClick() {
        if (loginModel == null) {
            return;
        }
        final String value = email.getValue();
        if (value == null || value.isEmpty()) {
            return;
        }
        LoginModel loginUser = new LoginModel(value);
        loginModel.setValue(loginUser);
    }

    public boolean isEmail(@NonNull String string) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }
}
