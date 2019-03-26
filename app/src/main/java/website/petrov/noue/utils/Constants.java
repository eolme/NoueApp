package website.petrov.noue.utils;

import androidx.annotation.IntDef;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Constants {
    public static final int FRAGMENT_PROJECTS = 3143;
    public static final int FRAGMENT_FEED = 4333;
    @NonNls
    public static final String FRAGMENT_TYPE = "fragmentType";

    public static final int REQUEST_FIRST_RUN = 48425476 % 65535;
    public static final int REQUEST_GET_ACCOUNTS = 13397652 % 65535;
    public static final int REQUEST_SUCCESS_LOGIN = 99586 % 65535;

    public static final int STATE_HIDDEN = 683336;
    public static final int STATE_VISIBLE = 4828593;
    public static final String ID_PROJECTS = "shortcut_projects";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_HIDDEN, STATE_VISIBLE})
    public @interface ApplicationState {
    }

    public static final String ID_FEED = "shortcut_feed";
    public static final String ACTION_PROJECTS = "website.petrov.noue.PROJECTS";
    public static final String ACTION_FEED = "website.petrov.noue.FEED";
    public static final String FEED_MODEL = "FeedModel";
    public static final String PROJECT_ITEM_MODEL = "ProjectItemModel";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FRAGMENT_PROJECTS, FRAGMENT_FEED})
    public @interface FragmentType {
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
        @NonNls
        public static final String STORAGE_INSTANCE_ID = "storage_instance_id";

        private Storage() {} // hide constructor
    }

    public static final class API {
        public static final String BASE_URL = "https://petrov.website/api/";

        public static final String METHOD_LOGIN = "noue.login";
        public static final String METHOD_FEED = "noue.feed";
        public static final String METHOD_PROJECTS = "noue.projects";
    }

    public static final class Debug {
        @NonNls
        public static final  String TAG = "[NOUE]";

        private Debug() {} // hide constructor
    }

    private Constants() {} // hide constructor
}
