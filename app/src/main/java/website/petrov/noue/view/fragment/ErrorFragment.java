package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;

public final class ErrorFragment extends Fragment {
    @Nullable
    private String message;

    public ErrorFragment(@NonNull String message) {
        this.message = message;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View self = inflater.inflate(R.layout.fragment_error, container, false);
        ((TextView) ViewCompat.requireViewById(self, R.id.fragment_error_message)).setText(message);
        return self;
    }
}
