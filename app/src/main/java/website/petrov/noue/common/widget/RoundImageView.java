package website.petrov.noue.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatImageView;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

import website.petrov.noue.R;

/**
 * A fast circular ImageView.
 */
public final class RoundImageView extends AppCompatImageView {
    @NonNull
    private final Paint mPaint;
    @NonNull
    private final Paint mPaintBackground;
    @Px
    private int mCanvasSize;
    @Nullable
    private ColorFilter mColorFilter;
    @Nullable
    private Bitmap mImage;
    @Nullable
    private Drawable mDrawable;

    public RoundImageView(@NonNull Context context) {
        this(context, null);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaintBackground = new Paint();
        mPaintBackground.setAntiAlias(true);

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        setFromColor(attributes.getColor(R.styleable.RoundImageView_color, Color.WHITE));
        setBackgroundColor(attributes.getColor(R.styleable.RoundImageView_background_color, Color.WHITE));
        attributes.recycle();
    }

    public void setFromColor(@ColorInt int color) {
        setImageDrawable(new ColorDrawable(color));
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        mPaintBackground.setColor(backgroundColor);
        invalidate();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (Objects.equals(mColorFilter, colorFilter)) {
            return;
        }

        mColorFilter = colorFilter;
        mDrawable = null;
        invalidate();
    }

    @Override
    @NonNull
    public ScaleType getScaleType() {
        final ScaleType currentScaleType = super.getScaleType();
        return currentScaleType != ScaleType.CENTER_INSIDE ? ScaleType.CENTER_CROP : currentScaleType;
    }

    @Override
    public void setScaleType(@NonNull ScaleType scaleType) {
        if (scaleType != ScaleType.CENTER_CROP && scaleType != ScaleType.CENTER_INSIDE) {
            throw new IllegalArgumentException();
        } else {
            super.setScaleType(scaleType);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        final Drawable currentDrawable = getDrawable();

        if (!Objects.equals(mDrawable, currentDrawable)) {
            mDrawable = currentDrawable;
            mImage = drawableToBitmap(mDrawable);
            updateShader();
        }

        if (mImage == null) {
            return;
        }

        if (!isInEditMode()) {
            mCanvasSize = Math.min(getHeight(), getWidth());
        }

        @Px final int circleCenter = mCanvasSize / 2;

        canvas.drawCircle(circleCenter, circleCenter, circleCenter, mPaintBackground);
        canvas.drawCircle(circleCenter, circleCenter, circleCenter, mPaint);
    }

    @Override
    protected void onSizeChanged(@Px int width, @Px int height, @Px int oldWidth, @Px int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mCanvasSize = Math.min(width, height);
        if (mImage != null) {
            updateShader();
        }
    }

    private void updateShader() {
        if (mImage == null) {
            return;
        }

        final BitmapShader shader = new BitmapShader(mImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float scale = 0;
        float dx = 0;
        float dy = 0;

        @Px final int width = getWidth();
        @Px final int height = getHeight();

        @Px final int imageWidth = mImage.getWidth();
        @Px final int imageHeight = mImage.getHeight();

        switch (getScaleType()) {
            case CENTER_CROP: {
                if (imageWidth * height > width * imageHeight) {
                    scale = height / (float) imageHeight;
                    dx = (width - imageWidth * scale) * 0.5f;
                } else {
                    scale = width / (float) imageWidth;
                    dy = (height - imageHeight * scale) * 0.5f;
                }
                break;
            }
            case CENTER_INSIDE: {
                if (imageWidth * height < width * imageHeight) {
                    scale = height / (float) imageHeight;
                    dx = (width - imageWidth * scale) * 0.5f;
                } else {
                    scale = width / (float) imageWidth;
                    dy = (height - imageHeight * scale) * 0.5f;
                }
                break;
            }
            case MATRIX:
            case FIT_XY:
            case FIT_START:
            case FIT_CENTER:
            case FIT_END:
            case CENTER:
            default: {
                break;
            }
        }

        final Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        shader.setLocalMatrix(matrix);

        mPaint.setShader(shader);
        mPaint.setColorFilter(mColorFilter);
    }

    @Px
    private int getDrawableWidth(@NonNull Drawable drawable) {
        return Math.max(drawable.getIntrinsicWidth(), getMeasuredWidth());
    }

    @Px
    private int getDrawableHeight(@NonNull Drawable drawable) {
        return Math.max(drawable.getIntrinsicHeight(), getMeasuredHeight());
    }

    @Contract("null -> null")
    @Nullable
    private Bitmap drawableToBitmap(@Nullable Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            final Bitmap bitmap = Bitmap.createBitmap(
                    getDrawableWidth(drawable),
                    getDrawableHeight(drawable),
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        @Px final int width = measureWidth(widthMeasureSpec);
        @Px final int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Px
    private int measureWidth(int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST: {
                return specSize;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                return mCanvasSize;
            }

        }
    }

    @Px
    private int measureHeight(int measureSpecHeight) {
        final int specMode = MeasureSpec.getMode(measureSpecHeight);
        final int specSize = MeasureSpec.getSize(measureSpecHeight);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST: {
                return specSize + 2;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                return mCanvasSize + 2;
            }
        }
    }
}
