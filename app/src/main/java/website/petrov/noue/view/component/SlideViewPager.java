package website.petrov.noue.view.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public final class SlideViewPager extends ViewPager {

    public SlideViewPager(@NonNull Context context) {
        super(context);
    }

    public SlideViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        final PagerAdapter mAdapter = this.getAdapter();
        final int count = mAdapter == null ? 0 : mAdapter.getCount();
        if (count == 0) {
            super.setCurrentItem(item, smoothScroll);
            return;
        }
        super.setCurrentItem(getOffsetAmount() + (item % count), smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        final PagerAdapter mAdapter = this.getAdapter();
        final int position = super.getCurrentItem();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return position;
        }
        if (mAdapter instanceof SlideAdapter) {
            return (position % ((SlideAdapter) mAdapter).getRealCount());
        } else {
            return position;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    private int getOffsetAmount() {
        final PagerAdapter mAdapter = this.getAdapter();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return 0;
        }
        if (mAdapter instanceof SlideAdapter) {
            return ((SlideAdapter) mAdapter).getRealCount() * 100;
        } else {
            return 0;
        }
    }
}
