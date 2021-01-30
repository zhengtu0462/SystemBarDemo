package com.zhengtu.statusbarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 状态栏高度View,用于沉浸占位
 * 用法：
 * use_height: 设置当前布局高度=状态栏高度值 用于无子View时的占位
 * use_padding_top: 设置当前顶部padding=状态栏高度值 用于有子View时的占位
 * <p>
 * //顶部的需要沉浸的图片View 或其他东西 视频View  等
 * <ImageView
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:background="@mipmap/icon_top_bg"
 * android:scaleType="centerCrop" />
 * <p>
 * // app:use_type="use_padding_top 向上paddingTop状态栏高度
 * <com.xxx.views.StatusBarHeightView
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * android:layout_alignParentEnd="true"
 * android:layout_marginEnd="@dimen/widget_size_5"
 * app:use_type="use_padding_top"
 * android:orientation="vertical" >
 * //这里放内容布局或View
 * <ImageView
 * android:id="@+id/ivUserShare"
 * android:layout_width="@dimen/title_height"
 * android:layout_height="@dimen/title_height"
 * android:padding="@dimen/widget_size_10"
 * android:src="@mipmap/icon_share_white" />
 * <p>
 * </com.xxx.views.StatusBarHeightView>
 * <p>
 * //不要忘记了, 在当前activity onCreate中设置 取消padding,  因为这个padding 我们用代码实现了,不需要系统帮我
 * StatusBarUtil.setRootViewFitsSystemWindows(this,false);
 */

public class StatusBarHeightView extends LinearLayout {
    private int statusBarHeight;
    private int type;

    public StatusBarHeightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StatusBarHeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public StatusBarHeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        } else {
            //低版本 直接设置0
            statusBarHeight = 0;
        }
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusBarHeightView);
            type = typedArray.getInt(R.styleable.StatusBarHeightView_use_type, 0);
            typedArray.recycle();
        }
        if (type == 1) {
            setPadding(getPaddingLeft(), statusBarHeight, getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (type == 0) {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    statusBarHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
