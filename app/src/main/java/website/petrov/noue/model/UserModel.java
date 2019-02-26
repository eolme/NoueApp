package website.petrov.noue.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.common.model.Model;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utilities.Provider;

public final class UserModel implements Model {
    private int id;
    @NonNull
    private String email;
    @Nullable
    private String name;
    @Nullable
    private String about;

    public UserModel(@NonNull String email) {
        this.email = email;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserModel)) {
            return false;
        }
        final UserModel comp = (UserModel) obj;
        return email.contentEquals(comp.email);
    }

    @Override
    public int hashCode() {
        return 17 ^ email.hashCode();
    }

    @NonNull
    @Contract(pure = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        StorageShared.setAccountEmail(email);

        this.email = email;
    }

    @Contract(pure = true)
    public String getName() {
        if (name == null) {
            name = StorageShared.getAccountName();
        }

        return name;
    }

    public void setName(@NonNull String name) {
        StorageShared.setAccountName(name);

        this.name = name;
    }

    @Contract(pure = true)
    public String getAbout() {
        if (about == null) {
            about = StorageShared.getAccountAbout();
        }

        return about;
    }

    public void setAbout(@NonNull String about) {
        StorageShared.setAccountAbout(about);

        this.about = about;
    }

    public boolean isValidEmail() {
        return Provider.isEmail(email);
    }
}
