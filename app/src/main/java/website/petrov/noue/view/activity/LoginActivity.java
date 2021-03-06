package website.petrov.noue.view.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.databinding.ActivityLoginBinding;
import website.petrov.noue.utils.Constants;
import website.petrov.noue.utils.ContextUtils;
import website.petrov.noue.viewmodel.LoginViewModel;

import static android.Manifest.permission.GET_ACCOUNTS;

public final class LoginActivity extends BaseActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private static final int time = 400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginViewModel);

        loginViewModel.getLoginModel().observe(this, loginUser -> {
            if (TextUtils.isEmpty(loginUser.getEmail())) {
                binding.emailLayout.setError(getString(R.string.error_field_required));
                binding.email.requestFocus();
            } else if (!loginUser.isValidEmail()) {
                binding.emailLayout.setError(getString(R.string.error_invalid_email));
                binding.email.requestFocus();
            } else {
                binding.emailLayout.setError(null);
                ContextUtils.clearFocus(binding.email);
                binding.loginProgress.setAlpha(0);
                binding.loginProgress.setVisibility(View.VISIBLE);
                binding.loginProgress.animate().setDuration(time).alpha(1);
                loginViewModel.attemptLogin(this);
            }
        });
    }

    private boolean mayRequestContacts() {
        if (ContextCompat.checkSelfPermission(this, GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, GET_ACCOUNTS)) {
            new AlertDialog
                    .Builder(LoginActivity.this)
                    .setTitle(R.string.info)
                    .setMessage(R.string.permission_rationale_accounts)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(this, new String[]{GET_ACCOUNTS}, Constants.REQUEST_GET_ACCOUNTS);
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{GET_ACCOUNTS}, Constants.REQUEST_GET_ACCOUNTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_GET_ACCOUNTS &&
                grantResults.length == 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            populateAutoComplete();
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        final Account[] accounts = AccountManager.get(LoginActivity.this).getAccounts();
        final List<String> emails = new ArrayList<>(accounts.length);

        String email;
        for (Account account : accounts) {
            email = account.name.toLowerCase(Locale.getDefault());
            if (!emails.contains(email) && ContextUtils.isEmail(email)) {
                emails.add(email);
            }
        }

        binding.email.setAdapter(new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, emails));
    }
}

