package website.petrov.noue.utilities;

import androidx.annotation.IntDef;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Constants {
    public static final int FRAGMENT_CARD = 3143;
    public static final int FRAGMENT_FEED = 4333;
    public static final int FRAGMENT_DEFAULT = -1;
    @NonNls
    public static final String FRAGMENT_TYPE = "fragmentType";

    public static final int REQUEST_FIRST_RUN = 48425476 % 65535;
    public static final int REQUEST_GET_ACCOUNTS = 13397652 % 65535;
    public static final int REQUEST_SUCCESS_LOGIN = 99586 % 65535;

    public static final int STATE_HIDDEN = 683336;
    public static final int STATE_VISIBLE = 4828593;

    public static final int TRANSPARENT_NAV = 0b000001;
    public static final int TRANSPARENT_STATUS = 0b000010;
    public static final int LIGHT_NAV = 0b000100;
    public static final int LIGHT_STATUS = 0b001000;
    public static final int LIGHT_UI = LIGHT_NAV | LIGHT_STATUS;
    public static final int TRANSPARENT_UI = TRANSPARENT_NAV | TRANSPARENT_STATUS;
    public static final int TRANSPARENT_CUSTOM = 0b010000;
    public static final int NO_LIMIT_UI = 0b100000;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FRAGMENT_CARD, FRAGMENT_FEED, FRAGMENT_DEFAULT})
    public @interface FragmentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_HIDDEN, STATE_VISIBLE})
    public @interface ApplicationState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TRANSPARENT_NAV, TRANSPARENT_STATUS, TRANSPARENT_UI, TRANSPARENT_CUSTOM, LIGHT_NAV,
            LIGHT_STATUS, LIGHT_UI, NO_LIMIT_UI})
    public @interface UIFlag {
    }

    public static final class Storage {
        @NonNls
        public static final String SHARED_PREFERENCES = "shared_storage";
        @NonNls
        public static final String STORAGE_FIRST_RUN = "storage_first_run";
        public static final boolean STORAGE_FIRST_RUN_DEFAULT = true;
        @NonNls
        public static final String STORAGE_ACCOUNT_NAME = "storage_account_name";
        @Nls
        public static final String STORAGE_ACCOUNT_NAME_DEFAULT = "";
        @NonNls
        public static final String STORAGE_ACCOUNT_ABOUT = "storage_account_about";
        @Nls
        public static final String STORAGE_ACCOUNT_ABOUT_DEFAULT = "";
        @NonNls
        public static final String STORAGE_ACCOUNT_EMAIL = "storage_account_email";
        @Nls
        public static final String STORAGE_ACCOUNT_EMAIL_DEFAULT = "";
    }

    public static final class Debug {
        @NonNls
        public static String TAG = "[NOUE]";
    }
}
