package website.petrov.noue.view.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.R;
import website.petrov.noue.common.activity.BaseActivity;
import website.petrov.noue.databinding.ActivityLoginBinding;
import website.petrov.noue.utilities.Constants;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.viewmodel.LoginViewModel;

import static android.Manifest.permission.GET_ACCOUNTS;

public final class LoginActivity extends BaseActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.google.setValue(GoogleSignIn.getClient(this, gso));

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginViewModel);

        loginViewModel.getLoginModel().observe(this, loginUser -> {
            if (TextUtils.isEmpty(loginUser.getEmail())) {
                binding.emailLayout.setError(getString(R.string.error_field_required));
                binding.email.requestFocus();
            } else if (!loginUser.isValid()) {
                binding.emailLayout.setError(getString(R.string.error_invalid_email));
                binding.email.requestFocus();
            } else {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(GET_ACCOUNTS)) {
            new AlertDialog
                    .Builder(LoginActivity.this)
                    .setTitle(R.string.info)
                    .setMessage(R.string.permission_rationale_accounts)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.cancel();
                        requestPermissions(new String[]{GET_ACCOUNTS}, Constants.REQUEST_GET_ACCOUNTS);
                    })
                    .show();
        } else {
            requestPermissions(new String[]{GET_ACCOUNTS}, Constants.REQUEST_GET_ACCOUNTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_GET_ACCOUNTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        final Account[] accounts = AccountManager.get(LoginActivity.this).getAccounts();
        final List<String> emails = new ArrayList<>();
        for (Account account : accounts) {
            if (loginViewModel.isEmail(account.name)) {
                emails.add(account.name);
            }
        }
        binding.email.setAdapter(new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, emails));
    }

    private void attemptLogin() {
        binding.emailLayout.setError(null);
        Provider.clearFocus(binding.email);

        binding.loginProgress.setAlpha(0);
        binding.loginProgress.setVisibility(View.VISIBLE);

        @IntegerRes final int time = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        binding.loginProgress.animate().setDuration(time).alpha(1).setListener(null);
    }
}

