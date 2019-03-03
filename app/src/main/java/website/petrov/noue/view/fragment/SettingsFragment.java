package website.petrov.noue.view.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

import website.petrov.noue.R;
import website.petrov.noue.utilities.Provider;

public final class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

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

    @Override
    public void onResume() {
        super.onResume();

        updatePrefenceSummary();
    }

    @Override
    protected void onBindPreferences() {
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onUnbindPreferences() {
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(@NonNull SharedPreferences sharedPreferences, @NonNull String key) {
        final Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            pref.setSummary(((EditTextPreference) pref).getText());
        }
    }

    @NonNull
    @Contract("_, _ -> param2")
    private ArrayList<Preference> getPreferenceList(@NonNull Preference pref, @NonNull ArrayList<Preference> list) {
        if (pref instanceof PreferenceGroup) {
            final PreferenceGroup pGroup = (PreferenceGroup) pref;
            final int count = pGroup.getPreferenceCount();
            for (int i = 0; i < count; ++i) {
                getPreferenceList(pGroup.getPreference(i), list);
            }
        } else {
            Log.d("Added", pref.getKey());
            list.add(pref);
        }
        return list;
    }

    private void updatePrefenceSummary() {
        final ArrayList<Preference> list = getPreferenceList(getPreferenceScreen(), new ArrayList<>());
        for (Preference pref : list) {
            if (pref instanceof EditTextPreference) {
                pref.setSummary(((EditTextPreference) pref).getText());
            }
        }
    }
}
