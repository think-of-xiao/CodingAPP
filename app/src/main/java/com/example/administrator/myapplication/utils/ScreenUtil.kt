package com.example.administrator.myapplication.utils

import android.content.Context
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window


class ScreenUtil {

    companion object {
        fun getScreenHeight(mContext: Context): Int {
            return mContext.resources.displayMetrics.heightPixels
        }

        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 获取状态栏高度
         */
        fun getStatusBarHeight(context: Context): Int {
            val frame = Rect()
            (context as AppCompatActivity).window.decorView.getWindowVisibleDisplayFrame(frame)
            return frame.top
        }

        /**
         * 获取标题栏高度
         */
        fun getTitleBarHeight(context: Context): Int {
            //statusBarHeight是上面所求的状态栏的高度
            val contentTop = (context as AppCompatActivity).window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
            return contentTop - getStatusBarHeight(context)
        }

        /**
         * 获取顶部高度（包括状态栏与标题栏）
         */
        fun getAppTopHeight(context: Context): Int {
            return (context as AppCompatActivity).window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        }
    }

}