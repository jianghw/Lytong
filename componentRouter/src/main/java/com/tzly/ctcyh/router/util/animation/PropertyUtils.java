package com.tzly.ctcyh.router.util.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;

/**
 * 属性动画 工具
 */

public class PropertyUtils {

    private PropertyUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void shakeView(View view, int duration) {
        //先小后大
        PropertyValuesHolder scale_x = getScaleValuesHolderX(View.SCALE_X);

        PropertyValuesHolder scale_y = getScaleValuesHolderY(View.SCALE_Y);

        PropertyValuesHolder rotation = getRotationValuesHolder();

        startObject(view, duration, scale_x, scale_y, rotation);
    }

    public static ObjectAnimator createShakeView(View view, int duration,int count) {
        //先小后大
        PropertyValuesHolder scale_x = getScaleValuesHolderX(View.SCALE_X);

        PropertyValuesHolder scale_y = getScaleValuesHolderY(View.SCALE_Y);

        PropertyValuesHolder rotation = getRotationValuesHolder();

        return createObject(view, duration,count, scale_x, scale_y, rotation);
    }


    public static PropertyValuesHolder getScaleValuesHolderX(Property<View, Float> scaleX) {
        return PropertyValuesHolder.ofKeyframe(
                scaleX,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, 0.8f),
                Keyframe.ofFloat(0.5f, 1.0f),
                Keyframe.ofFloat(0.75f, 1.2f),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
    }

    public static PropertyValuesHolder getScaleValuesHolderY(Property<View, Float> scaleY) {
        return PropertyValuesHolder.ofKeyframe(
                scaleY,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, 0.8f),
                Keyframe.ofFloat(0.5f, 1.0f),
                Keyframe.ofFloat(0.75f, 1.2f),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
    }

    public static PropertyValuesHolder getRotationValuesHolder() {
        float shakeDegrees = 10.0f;
        return PropertyValuesHolder.ofKeyframe(
                View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );
    }

    public static void startObject(View view, int duration, PropertyValuesHolder... valuesHolders) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolders);
        objectAnimator.setDuration(duration);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(4);
        objectAnimator.start();
    }

    public static ObjectAnimator createObject(View view, int duration, int count, PropertyValuesHolder... valuesHolders) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolders);
        objectAnimator.setDuration(duration);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(count);
        return objectAnimator;
    }
}
