package website.petrov.noue.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.common.model.BaseModel;

public final class LoginModel extends BaseModel {
    private final String email;

    public LoginModel(@NonNull String email) {
        this.email = email;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LoginModel)) {
            return false;
        }
        final LoginModel comp = (LoginModel) obj;
        return email.contentEquals(comp.email);
    }

    @Override
    public int hashCode() {
        int hash = 17;

        hash ^= email.hashCode();

        return hash;
    }


    @Contract(pure = true)
    public String getEmail() {
        return email;
    }

    public boolean isValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
