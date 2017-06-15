package cn.qqtheme.framework.util;

import java.util.List;

/**
 * Created by jianghw on 2017/6/13.
 * Description: 权限监听
 * Update by:
 * Update day:
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
