package cn.qqtheme.framework.widght;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import cn.qqtheme.framework.R;


public class CustomLoader extends View {
    private Paint paint = new Paint();
    private RectF rect = new RectF();
    //属性
    private String text;
    private int textSize;
    private int textColor;
    private int mImageId;
    private int imageHeight;
    private int spacing;//图片和文字的间距
    private int backgroundColor;
    private int cornerRadius;
    private int cycle;//旋转周期
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    //运行时
    private float currentdegrees;
    private Bitmap bitmap;

    //    RotateAnimation animation;
    private ValueAnimator valueAnimator;

    public CustomLoader(Context context) {
        super(context);
    }

    public CustomLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customLoader);

        text = typedArray.getString(R.styleable.customLoader_text);
        text = text == null ? "" : text.trim();

        textSize = (int) typedArray.getDimension(R.styleable.customLoader_textSize, dip2px(this.getContext(), 16));
        textColor = typedArray.getColor(R.styleable.customLoader_textColor, 0);
        if (textColor == 0)
            textColor = typedArray.getResourceId(R.styleable.customLoader_textColor, 0);
        if (textColor == 0)
            textColor = 0xFF000000;
        mImageId = typedArray.getResourceId(R.styleable.customLoader_image, 0);
        imageHeight = (int) typedArray.getDimension(R.styleable.customLoader_imageHeight, dip2px(this.getContext(), 50));

        backgroundColor = typedArray.getColor(R.styleable.customLoader_backgroundColor, 0);
        if (backgroundColor == 0)
            backgroundColor = typedArray.getResourceId(R.styleable.customLoader_backgroundColor, 0);

        spacing = (int) typedArray.getDimension(R.styleable.customLoader_spacing, dip2px(this.getContext(), 10));

        cornerRadius = (int) typedArray.getDimension(R.styleable.customLoader_cornerRadius, dip2px(this.getContext(), 5));

        cycle = typedArray.getInt(R.styleable.customLoader_cycle, 1000);

        paddingLeft = (int) typedArray.getDimension(R.styleable.customLoader_paddingLeft, dip2px(this.getContext(), 5));
        paddingTop = (int) typedArray.getDimension(R.styleable.customLoader_paddingTop, dip2px(this.getContext(), 5));
        paddingRight = (int) typedArray.getDimension(R.styleable.customLoader_paddingRight, dip2px(this.getContext(), 5));
        paddingBottom = (int) typedArray.getDimension(R.styleable.customLoader_paddingBottom, dip2px(this.getContext(), 5));

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        startAnimation();
    }

    /**
     * 计算组件宽度
     */
    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getDefaultWidth();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算组件高度
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getDefaultHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算默认宽度
     */
    private int getDefaultWidth() {
        int width = 0;
        if (this.text != null && !this.text.trim().equals("")) {
            paint.setTextSize(this.textSize);
            width = (int) this.paint.measureText(this.text);
        }
        if (this.mImageId != 0) {
            width = this.imageHeight > width ? this.imageHeight : width;
        }
        width += this.paddingLeft;
        width += this.paddingRight;
        return width;
    }

    /**
     * 计算默认宽度
     */
    private int getDefaultHeight() {
        int height = 0;
        if (this.text != null && !this.text.trim().equals("")) {
            paint.setTextSize(this.textSize);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
            height += txtHeight;
            height += this.spacing;
        }
        if (this.mImageId != 0) {
            height += this.imageHeight;
        }
        height += this.paddingTop;
        height += this.paddingBottom;
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            drawBackground(canvas);
            drawImage(canvas);
            drawText(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 画背景
     */
    private void drawBackground(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = this.getWidth();
        rect.bottom = this.getHeight();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.backgroundColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
    }

    /**
     * 画图片
     */
    private void drawImage(Canvas canvas) {
        if (this.bitmap == null) {
            bitmap = BitmapFactory.decodeResource(this.getResources(), this.mImageId);
            bitmap = resizeBitmap_Height(bitmap, this.imageHeight);
        }
        if (this.bitmap == null)
            return;
        int contentWidth = this.getWidth() - this.paddingLeft - this.paddingRight;
        int contentHeight = this.getHeight() - this.paddingTop - this.paddingBottom;
        if (this.text != null && !this.text.trim().equals("")) {
            paint.setTextSize(this.textSize);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
            contentHeight -= txtHeight;
            contentHeight -= this.spacing;
        }
        int left = this.paddingLeft + (contentWidth / 2 - this.bitmap.getWidth() / 2);
        int top = this.paddingTop + (contentHeight / 2 - this.bitmap.getHeight() / 2);
        rect.left = left - 1;
        rect.top = top - 1;
        rect.right = left + this.bitmap.getWidth() + 1;
        rect.bottom = top + this.bitmap.getHeight() + 1;
        canvas.save();
        canvas.clipRect(rect);
        canvas.rotate(currentdegrees, rect.left + rect.width() / 2, rect.top + rect.height() / 2);
        canvas.drawBitmap(this.bitmap, left, top, null);
        canvas.restore();
    }

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        if (this.text == null || this.text.trim().equals(""))
            return;
        paint.setTextSize(this.textSize);
        paint.setColor(this.textColor);
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
        int txtWidth = (int) this.paint.measureText(this.text);
        int left = this.paddingLeft + ((this.getWidth() - this.paddingLeft - this.paddingRight) / 2 - txtWidth / 2);
        int top = this.paddingTop;
        if (this.bitmap != null) {
            top += this.bitmap.getHeight();
            top += this.spacing;
        }
        top = top + (this.getHeight() - top - this.paddingBottom) / 2 - txtHeight / 2;
        canvas.drawText(text, left, top - fontMetrics.ascent, paint);
    }

    /**
     * 根据宽度缩放图片
     */
    public static Bitmap resizeBitmap_Height(Bitmap bitmap, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleSize = ((float) h) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleSize, scaleSize);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } else {
            return null;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 开启动画
     */
//    public void startAnimation() {
//        animation = new RotateAnimation();
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setDuration(this.cycle);
//        animation.setRepeatCount(-1);
//        startAnimation(animation);
//    }

    /**
     * ValueAnimation.RESTART时,表示正序重新开始，
     * 当取值为ValueAnimation.REVERSE表示倒序重新开始
     * setRepeatCount(int value)用于设置动画循环次数,设置为0表示不循环，
     * 设置为ValueAnimation.INFINITE表示无限循环。
     */
    public void startAnimation() {
        if (valueAnimator != null && valueAnimator.isRunning()) cancelAnimation();
        valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentdegrees = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(this.cycle);
        valueAnimator.start();
    }

/*    public void cancelAnimation() {
        if (animation != null) {
            animation.cancel();
            clearAnimation();
        }
    }*/

    public void cancelAnimation() {
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
    }

    public void AnimationControlCenter(boolean start) {
        if (start) {
            startAnimation();
        } else {
            cancelAnimation();
        }
    }

    /**
     * 旋转动画实现类
     */
    private class RotateAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            currentdegrees = (int) (359 * interpolatedTime);
            invalidate();
        }
    }
}
