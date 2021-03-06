package com.jcodecraeer.xrecyclerview.touch;

/**
 * Created by jianghw on 2017/5/2.
 */

public interface  Closeable {
    /**
     * Smooth closed the menu.
     */
    void smoothCloseMenu();

    /**
     * Smooth closed the menu on the left.
     */
    void smoothCloseLeftMenu();

    /**
     * Smooth closed the menu on the right.
     */
    void smoothCloseRightMenu();

    /**
     * Smooth closed the menu for the duration.
     *
     * @param duration duration time.
     */
    void smoothCloseMenu(int duration);
}
