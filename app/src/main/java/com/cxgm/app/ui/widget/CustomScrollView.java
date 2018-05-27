package com.cxgm.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *    有时候我们需要监听ScroView的滑动情况，比如滑动了多少距离，
 *    是否滑到布局的顶部或者底部。
 *    可惜的是SDK并没有相应的方法，不过倒是提供了一个
 *     方法，显然这个方法是不能被外界调用的，因此就需要把它暴露出去，方便使用。解决方式就是写一个接口，
 */
public class CustomScrollView extends ScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    /**
     * 设置滚动接口
     * @param
     */

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    /**
     *
     *定义一个滚动接口
     * */

    public interface OnScrollChangeListener{
        void onScrollChanged(CustomScrollView scrollView,int l, int t, int oldl, int oldt);
    }

    /**
     * 当scrollView滑动时系统会调用该方法,并将该回调放过中的参数传递到自定义接口的回调方法中,
     * 达到scrollView滑动监听的效果
     *
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangeListener!=null){
            mOnScrollChangeListener.onScrollChanged(this,l,t,oldl,oldt);

        }
    }


}
