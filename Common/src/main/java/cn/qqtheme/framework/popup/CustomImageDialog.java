package cn.qqtheme.framework.popup;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by jianghw on 2017/7/13.
 * Description:
 * Update by:
 * Update day:
 */

public class CustomImageDialog extends Dialog {

    public CustomImageDialog(@NonNull Context context) {
        super(context);
    }

    public CustomImageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomImageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
