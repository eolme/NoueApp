package website.petrov.noue.common.component;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public final class SnapHelper extends LinearSnapHelper {
    @Nullable
    private OrientationHelper mHelper;

    public SnapHelper() {
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        }

        return out;
    }

    @Override
    @Nullable
    public View findSnapView(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager && layoutManager.canScrollHorizontally()) {
            return getStartView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return super.findSnapView(layoutManager);
    }

    private int distanceToStart(@NonNull View targetView, @NonNull OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    @Nullable
    private View getStartView(@NonNull RecyclerView.LayoutManager layoutManager,
                              @NonNull OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

            boolean isLastItem = ((LinearLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1;

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null;
            }

            final View child = layoutManager.findViewByPosition(firstChild);

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                    return null;
                } else {
                    return layoutManager.findViewByPosition(firstChild + 1);
                }
            }
        }

        return super.findSnapView(layoutManager);
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHelper == null) {
            mHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHelper;
    }
}
