package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.utilities.Provider;

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
        ((AppCompatTextView) Provider.getView(R.id.fragment_error_message, self)).setText(message);
        return self;
    }
}
