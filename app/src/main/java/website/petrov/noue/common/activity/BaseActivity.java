package website.petrov.noue.common.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getCallingActivity() != null) {
            setResult(RESULT_CANCELED, null);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected void dispatchBackPressed() {
        super.onBackPressed();
    }
}
