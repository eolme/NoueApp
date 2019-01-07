package website.petrov.noue.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import website.petrov.noue.R;
import website.petrov.noue.utilities.Provider;

public final class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle bundle, @Nullable String s) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

    @Override
    @NonNull
    public RecyclerView onCreateRecyclerView(@NonNull LayoutInflater inflater,
                                             @NonNull ViewGroup parent,
                                             @Nullable Bundle savedInstanceState) {
        final RecyclerView view = super.onCreateRecyclerView(inflater, parent, savedInstanceState);
        final Activity context = super.getActivity();
        if (context != null) {
            view.setPaddingRelative(0, 0, 0,
                    Provider.getNavigationBarHeight(context) +
                            (int) getResources().getDimension(R.dimen.margin_scroll_default)
            );
        }
        return view;
    }
}
