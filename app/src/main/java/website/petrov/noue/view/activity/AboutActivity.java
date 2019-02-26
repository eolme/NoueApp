package website.petrov.noue.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import website.petrov.noue.R;

public final class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(AboutActivity.this, R.layout.activity_about);
    }
//
//    public void openGooglePlay(@NonNull View view) {
//        final String appPackageName = getPackageName();
//        try {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//        } catch (Exception ignored) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//        }
//    }
//
//    public void openAppStore(@NonNull View view) {
//        Snackbar.make(view.getRootView(), getString(R.string.soon), Snackbar.LENGTH_SHORT).show();
//    }
//
//    public void openWeb(@NonNull View view) {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://petrov.website/completed/noue/")));
//    }
}
