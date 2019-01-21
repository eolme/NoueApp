package website.petrov.noue.repository.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static website.petrov.noue.utilities.Constants.Storage;

public final class StorageShared {
    @Nullable
    private static SharedPreferences mStorage;
    @Nullable
    private static List<UpdateListener> mListeners;

    public static void initialize(@NonNull Application app) {
        mStorage = app.getSharedPreferences(Storage.SHARED_PREFERENCES, MODE_PRIVATE);
    }

    private static void needUpdate() {
        if (mListeners != null) {
            for (@Nullable UpdateListener listener : mListeners) {
                if (listener != null) {
                    listener.onStorageUpdate();
                }
            }
        }
    }

    @NonNull
    public static String getAccountName() {
        if (mStorage == null) {
            return Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
        }
        final String storageString = mStorage.getString(Storage.STORAGE_ACCOUNT_NAME, Storage.STORAGE_ACCOUNT_NAME_DEFAULT);
        return storageString != null ? storageString : Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
    }

    public static void setAccountName(@NonNull String name) {
        if (mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_ACCOUNT_NAME, name);
        editor.apply();

        needUpdate();
    }

    @NonNull
    public static String getAccountAbout() {
        if (mStorage == null) {
            return Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
        }
        final String storageString = mStorage.getString(Storage.STORAGE_ACCOUNT_ABOUT, Storage.STORAGE_ACCOUNT_ABOUT_DEFAULT);
        return storageString != null ? storageString : Storage.STORAGE_ACCOUNT_ABOUT_DEFAULT;
    }

    public static void setAccountAbout(@NonNull String name) {
        if (mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_ACCOUNT_ABOUT, name);
        editor.apply();

        needUpdate();
    }

    public static void register(@Nullable UpdateListener handler) {
        if (handler == null) {
            return;
        }

        if (mListeners == null) {
            mListeners = new LinkedList<>();
        }

        mListeners.add(handler);
    }

    public static void unregister(@Nullable UpdateListener handler) {
        if (handler == null) {
            return;
        }

        if (mListeners != null) {
            mListeners.remove(handler);
        }
    }

    @NonNull
    public static String getAccountEmail() {
        if (mStorage == null) {
            return Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
        }
        final String storageString = mStorage.getString(Storage.STORAGE_ACCOUNT_EMAIL, Storage.STORAGE_ACCOUNT_EMAIL_DEFAULT);
        return storageString != null ? storageString : Storage.STORAGE_ACCOUNT_EMAIL_DEFAULT;
    }

    public static void setAccountEmail(@NonNull String name) {
        if (mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_ACCOUNT_EMAIL, name);
        editor.apply();

        needUpdate();
    }

    public static boolean getFirstRunFlag() {
        if (mStorage == null) {
            return Storage.STORAGE_FIRST_RUN_DEFAULT;
        }
        return mStorage.getBoolean(Storage.STORAGE_FIRST_RUN, Storage.STORAGE_FIRST_RUN_DEFAULT);
    }

    public static void setFirstRunFlag(boolean flag) {
        if (mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putBoolean(Storage.STORAGE_FIRST_RUN, flag);
        editor.apply();

        needUpdate();
    }

    @Override
    protected void finalize() throws Throwable {
        if (mListeners != null) {
            mListeners.clear();
        }
        super.finalize();
    }

    public interface UpdateListener {
        void onStorageUpdate();
    }
}
