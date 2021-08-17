package com.gdu.myapplicationac103;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Description:
 * Created by Czm on 2021/8/14 15:34.
 */
public class YzService extends AccessibilityService {

    private static final String TAG = "YzService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: ");
        List<AccessibilityNodeInfo> nodeInfoList = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.gdu.myapplicationac103:id/tc");
        String wxCode = "";
        if (nodeInfoList != null && nodeInfoList.size() > 0) {
            wxCode = nodeInfoList.get(0).getText().toString();
        }
        Log.d(TAG, "onAccessibilityEvent: " + wxCode + "   -- " + (nodeInfoList == null ? "0" : "" + nodeInfoList.size()));
    }


    @Override
    public void onInterrupt() {

    }
}
