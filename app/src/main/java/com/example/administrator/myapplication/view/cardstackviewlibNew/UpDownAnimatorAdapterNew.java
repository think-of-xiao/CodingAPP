package com.example.administrator.myapplication.view.cardstackviewlibNew;

import android.animation.ObjectAnimator;
import android.view.View;

public class UpDownAnimatorAdapterNew extends AnimatorAdapterNew {

    public UpDownAnimatorAdapterNew(CardStackViewNew cardStackView) {
        super(cardStackView);
    }

    protected void itemExpandAnimatorSet(final CardStackViewNew.ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        itemView.clearAnimation();
        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), mCardStackView.getScrollY() + mCardStackView.getPaddingTop());
        mSet.play(oa);
        int collapseShowItemCount = 0;
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            int childTop;
            if (i == mCardStackView.getSelectPosition()) continue;
            final View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            if (i > mCardStackView.getSelectPosition() && collapseShowItemCount < mCardStackView.getNumBottomShow()) {
                childTop = mCardStackView.getShowHeight() - getCollapseStartTop(collapseShowItemCount) + mCardStackView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
                collapseShowItemCount++;
            } else if (i < mCardStackView.getSelectPosition()) {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), mCardStackView.getScrollY() - child.getHeight());
                mSet.play(oAnim);
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), mCardStackView.getShowHeight() + mCardStackView.getScrollY());
                mSet.play(oAnim);
            }
        }
    }

    @Override
    protected void itemCollapseAnimatorSet(CardStackViewNew.ViewHolder viewHolder) {
        int childTop = mCardStackView.getPaddingTop();
        for (int i = 0; i < mCardStackView.getChildCount(); i++) {
            View child = mCardStackView.getChildAt(i);
            child.clearAnimation();
            final CardStackViewNew.LayoutParams lp =
                    (CardStackViewNew.LayoutParams) child.getLayoutParams();
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
