package com.sh3h.meterreading.ui.InspectionInput.tools;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/4/30 13:54
 */
public class ClickUtils {

    public static Long mLastClickTime = 0L;

    public static boolean singleClick(Long lastClickTime) {
        ;
        Long currentTime = System.currentTimeMillis();
        if ((currentTime - mLastClickTime) > 600) {
            mLastClickTime = lastClickTime;
            return true;
        } else {
            return false;
        }
    }
}