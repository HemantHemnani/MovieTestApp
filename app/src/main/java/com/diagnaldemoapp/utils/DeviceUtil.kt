package com.diagnaldemoapp.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

public class DeviceUtil {

    companion object {

        /*
        * 0.75 - ldpi
        * 1.0 - mdpi
        * 1.5 - hdpi
        * 2.0 - xhdpi
        * 3.0 - xxhdpi
        * 4.0 - xxxhdpi
        * */
        fun getDeviceDensity(context: Context): String {
            var densityStr = "unknown"
            val density = context.resources.displayMetrics.density
            if (density.toDouble() == 0.75) {
                densityStr = "ldpi"
            } else if (density.toDouble() == 1.0) {
                densityStr = "mdpi"
            } else if (density.toDouble() == 1.5) {
                densityStr = "hdpi"
            } else if (density.toDouble() == 2.0) {
                densityStr = "xhdpi"
            } else if (density.toDouble() == 3.0) {
                densityStr = "xxhdpi"
            } else if (density.toDouble() == 4.0) {
                densityStr = "xxxhdpi"
            }
            return densityStr

        }


        /*
        * get device width
        * */
        fun getScreenWidth(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        fun getScreenHeight(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

    }
}