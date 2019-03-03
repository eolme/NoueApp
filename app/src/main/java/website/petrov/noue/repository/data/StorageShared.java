package website.petrov.noue.repository.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static website.petrov.noue.utilities.Constants.Storage;

public final class StorageShared {
    @Nullable
    private static SharedPreferences mStorage;
    @Nullable
    private static List<WeakReference<UpdateListener>> mListeners;

    public static void initialize(@NonNull Application app) {
        mStorage = app.getSharedPreferences(Storage.SHARED_PREFERENCES, MODE_PRIVATE);
    }

    private static void needUpdate() {
        if (mListeners != null) {
            UpdateListener currentUpdateListener;
            for (WeakReference<UpdateListener> listener : mListeners) {
                currentUpdateListener = listener.get();
                if (currentUpdateListener != null) {
                    currentUpdateListener.onStorageUpdate();
                }
            }
        }
    }

    @NonNull
    public static String getAccountName() {
        if (mStorage == null) {
            return Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
        }
        return mStorage.getString(Storage.STORAGE_ACCOUNT_NAME, Storage.STORAGE_ACCOUNT_NAME_DEFAULT);
    }

    public static void setAccountName(@Nullable String name) {
        if (name == null || mStorage == null) {
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
        return mStorage.getString(Storage.STORAGE_ACCOUNT_ABOUT, Storage.STORAGE_ACCOUNT_ABOUT_DEFAULT);
    }

    public static void setAccountAbout(@Nullable String about) {
        if (about == null || mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_ACCOUNT_ABOUT, about);
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

        mListeners.add(new WeakReference<>(handler));
    }

    @NonNull
    public static String getAccountEmail() {
        if (mStorage == null) {
            return Storage.STORAGE_ACCOUNT_NAME_DEFAULT;
        }
        return mStorage.getString(Storage.STORAGE_ACCOUNT_EMAIL, Storage.STORAGE_ACCOUNT_EMAIL_DEFAULT);
    }

    public static void setAccountEmail(@Nullable String email) {
        if (email == null || mStorage == null) {
            return;
        }
        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_ACCOUNT_EMAIL, email);
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

    public static String getInstanceId() {
        final String uuid = UUID.randomUUID().toString();

        if (mStorage == null) {
            return uuid;
        }

        if (mStorage.contains(Storage.STORAGE_INSTANCE_ID)) {
            return mStorage.getString(Storage.STORAGE_INSTANCE_ID, uuid);
        }

        final Editor editor = mStorage.edit();
        editor.putString(Storage.STORAGE_INSTANCE_ID, uuid);
        editor.apply();

        return uuid;
    }

    @Override
    protected void finalize() throws Throwable {
        if (mListeners != null) {
            for (WeakReference<UpdateListener> listener : mListeners) {
                listener.enqueue();
            }
            mListeners.clear();
        }
        super.finalize();
    }

    public interface UpdateListener {
        void onStorageUpdate();
    }
}
