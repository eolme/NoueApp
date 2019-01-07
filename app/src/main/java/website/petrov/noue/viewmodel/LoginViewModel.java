package website.petrov.noue.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import website.petrov.noue.common.viewmodel.BaseDynamicViewModel;
import website.petrov.noue.model.LoginModel;

public final class LoginViewModel extends BaseDynamicViewModel {
    @NonNull
    public MutableLiveData<GoogleSignInClient> google = new MutableLiveData<>();
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

    public void onGoogleClick() {

    }

    public boolean isEmail(@NonNull String string) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }

    @Override
    protected void load() {

    }
}
