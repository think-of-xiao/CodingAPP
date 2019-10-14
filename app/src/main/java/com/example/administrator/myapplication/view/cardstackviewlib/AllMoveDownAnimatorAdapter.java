package com.example.administrator.myapplication.view.cardstackviewlib;

import android.animation.ObjectAnimator;
import android.view.View;

import com.example.administrator.myapplication.utils.ScreenUtil;

public class AllMoveDownAnimatorAdapter extends AnimatorAdapter {

    public AllMoveDownAnimatorAdapter(CardStackView cardStackView) {
        super(cardStackView);
    }

    @Override
    protected void itemExpandAnimatorSet(final CardStackView.ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        itemView.clearAnimation();
        //设置选中的item项往上走的动画
        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), mCardStackView.getScrollY() + mCardStackView.getPaddingTop());
        mSet.play(oa);
        int collapseShowItemCount = 0;
        //遍历其它item项，设置它们的位移动画
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            int childTop;
            if (i == mCardStackView.getSelectPosition()) continue; //如果是选中项，退回执行下一个循环
            final View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            // 修改不用mCardStackView.getShowHeight()了，这里为调用屏幕高度还要减去垂直方向其它控件所占的高度，
            // 这样做可以不管点击的是哪个item，往下掉的其他item都能出现在屏幕下方显示
            // 减去30像素是因为activity布局中顶部加了个高度为15dp的View布局做站位以及卡片头上的那行添加银行卡布局大概30dp样子，换成px像素差不多就是60px
            int mShowHeight = ScreenUtil.Companion.getScreenHeight(mCardStackView.getContext()) - ScreenUtil.Companion.getAppTopHeight(mCardStackView.getContext()) - 30 - 60;
            //如果遍历到的item下标大于当前选中的item下标，并且下面显示的item数量小于我们初始化时设置的数量
            if (i > mCardStackView.getSelectPosition() && collapseShowItemCount < mCardStackView.getNumBottomShow()) {
                childTop = mShowHeight - getCollapseStartTop(collapseShowItemCount) + mCardStackView.getScrollY();
                // 修改了下滑高度的结束值，为的是显示上方箭头以及最前面的item显示出来的高度
                // 这里是设置的当前选中item的下一项item头上有向上箭头显示，为了不被箭头布局占位得减去它的高度60px（代码中设置的30dp）
                // 以及减去往下掉的item整体设置往上移了60px，这也就是122px的由来
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), (i == mCardStackView.getSelectPosition() + 1) ? childTop - 122 : childTop - 60);
                mSet.play(oAnim);
                collapseShowItemCount++;
            } else {
                childTop = mShowHeight + mCardStackView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), (i == mCardStackView.getSelectPosition() - 1) ? childTop - 122 : childTop - 100);
                mSet.play(oAnim);
            }
        }
    }

    @Override
    protected void itemCollapseAnimatorSet(CardStackView.ViewHolder viewHolder) {
        int childTop = mCardStackView.getPaddingTop();
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            final CardStackView.LayoutParams lp =
                    (CardStackView.LayoutParams) child.getLayoutParams();
            childTop += lp.topMargin;
            if (i != 0) {
                childTop -= mCardStackView.getOverlapGaps() * 2;
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            }
            childTop += lp.mHeaderHeight;
        }
    }

}
