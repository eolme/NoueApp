package website.petrov.noue.common.animation;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public final class FadePageTransformer implements ViewPager.PageTransformer {
    public void transformPage(@NonNull View view, float position) {
        view.setTranslationX(view.getWidth() * -position * 0.9F);
        if (position <= -1.0F || position >= 1.0F) {
            view.setAlpha(0.0F);
        } else if (position == 0.0F) {
            view.setAlpha(1.0F);
        } else {
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}