package com.example.administrator.myapplication.view.cardstackviewlib;

import android.animation.ObjectAnimator;
import android.view.View;

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
            if (i > mCardStackView.getSelectPosition() && collapseShowItemCount < mCardStackView.getNumBottomShow()) {
                childTop = mCardStackView.getShowHeight() - getCollapseStartTop(collapseShowItemCount) + mCardStackView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
                collapseShowItemCount++;
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), mCardStackView.getShowHeight() + mCardStackView.getScrollY());
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
