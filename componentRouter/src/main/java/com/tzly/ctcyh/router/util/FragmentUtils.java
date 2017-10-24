package com.tzly.ctcyh.router.util;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by jianghw on 2017/10/23.
 * Description:Fragment相关工具类
 * Update by:
 * Update day:
 */

public class FragmentUtils {

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId) {
        add(fm, add, containerId, false, false);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isHide      是否隐藏
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide) {
        add(fm, add, containerId, isHide, false);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isHide      是否隐藏
     * @param isAddStack  是否入回退栈
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide,
                           final boolean isAddStack) {
        putArgs(add, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isAddStack  是否入回退栈
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param add          要新增的fragment
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 新增fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param add          要新增的fragment
     * @param isAddStack   是否入回退栈
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm             fragment管理器
     * @param add            新增的fragment
     * @param containerId    布局Id
     * @param sharedElements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @NonNull final View... sharedElements) {
        add(fm, add, containerId, false, sharedElements);
    }

    /**
     * 新增fragment
     *
     * @param fm             fragment管理器
     * @param add            新增的fragment
     * @param containerId    布局Id
     * @param isAddStack     是否入回退栈
     * @param sharedElements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param add         新增的fragment
     * @param containerId 布局Id
     * @param showIndex   要显示的fragment索引
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> add,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, add.toArray(new Fragment[add.size()]), containerId, showIndex);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param add         新增的fragment
     * @param containerId 布局Id
     * @param showIndex   要显示的fragment索引
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment[] add,
                           @IdRes final int containerId,
                           final int showIndex) {
        for (int i = 0, len = add.length; i < len; ++i) {
            putArgs(add[i], new Args(containerId, showIndex != i, false));
        }
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    private static void addAnim(final FragmentTransaction ft,
                                final int enter,
                                final int exit,
                                final int popEnter,
                                final int popExit) {
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    private static void addSharedElement(final FragmentTransaction ft,
                                         final View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    private static void operateNoAnim(final FragmentManager fm,
                                      final int type,
                                      final Fragment src,
                                      Fragment... dest) {
        FragmentTransaction ft = fm.beginTransaction();
        operate(type, fm, ft, src, dest);
    }

    /**
     * 操作类
     */
    private static void operate(final int type,
                                final FragmentManager manager,
                                final FragmentTransaction transaction,
                                final Fragment src,
                                final Fragment... dest) {
        if (src != null && src.isRemoving()) {
            Log.e("FragmentUtils", src.getClass().getName() + " is isRemoving");
            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : dest) {
                    name = fragment.getClass().getName();
                    args = fragment.getArguments();
                    Fragment fragmentByTag = manager.findFragmentByTag(name);
                    if (fragmentByTag != null) {
                        if (fragmentByTag.isAdded()) {
                            transaction.remove(fragmentByTag);
                        }
                    }
                    transaction.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) transaction.hide(fragment);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) transaction.addToBackStack(name);
                }
                break;
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : dest) {
                    transaction.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : dest) {
                    transaction.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                transaction.show(src);
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        transaction.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                name = dest[0].getClass().getName();
                args = dest[0].getArguments();
                transaction.replace(args.getInt(ARGS_ID), dest[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) transaction.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        transaction.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = dest.length - 1; i >= 0; --i) {
                    Fragment fragment = dest[i];
                    if (fragment == dest[0]) {
                        if (src != null) transaction.remove(fragment);
                        break;
                    }
                    transaction.remove(fragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 封装的功能对象
     */
    private static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        private Args(final int id, final boolean isHide, final boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

}
