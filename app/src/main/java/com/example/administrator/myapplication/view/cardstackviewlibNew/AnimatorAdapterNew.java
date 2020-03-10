package com.example.administrator.myapplication.view.cardstackviewlibNew;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

public abstract class AnimatorAdapterNew {
    static final int ANIMATION_DURATION = 400;

    protected CardStackViewNew mCardStackView;
    protected AnimatorSet mSet;

    public AnimatorAdapterNew(CardStackViewNew cardStackView) {
        mCardStackView = cardStackView;
    }

    protected void initAnimatorSet() {
        mSet = new AnimatorSet();
        mSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mSet.setDuration(getDuration());
    }

    // 添加flag的作用为只有在全部收缩的时候点击item才展开，其它情况点击全部为收缩还原（0的时候展开）
    private int keepOneFlag = 0;

    public void itemClick(final CardStackViewNew.ViewHolder viewHolder, int position) {
        if (mSet != null && mSet.isRunning()) return;
        initAnimatorSet();
        if (mCardStackView.getSelectPosition() == position) {
            keepOneFlag = 0;
            Log.w("点击的是同一项item", mCardStackView.getSelectPosition()+"----------"+position);
            onItemCollapse(viewHolder);
        } else {
            Log.w("点击的是不同项item", mCardStackView.getSelectPosition()+"----------"+position);
            if (keepOneFlag == 1){
                keepOneFlag = 0;
                // 这种做法bug太多
//                onItemCollapse(viewHolder);
                // 直接调用cardStackView中clearScrollYAndTranslation方法回归初始状态就行
                mCardStackView.clearScrollYAndTranslation();
            }else if(keepOneFlag == 0) {
                keepOneFlag = 1;
                onItemExpand(viewHolder, position);
            }
        }
        if (mCardStackView.getChildCount() == 1)
            mSet.end();
    }

    /**
     * 点击item展开时其它item往下走的动画
     * @param viewHolder item布局的viewHolder
     * @param position 点击的item下标
     */
    protected abstract void itemExpandAnimatorSet(CardStackViewNew.ViewHolder viewHolder, int position);

    /**
     * item的收缩动画
     * @param viewHolder item布局的viewHolder
     */
    protected abstract void itemCollapseAnimatorSet(CardStackViewNew.ViewHolder viewHolder);

    private void onItemExpand(final CardStackViewNew.ViewHolder viewHolder, int position) {
        final int preSelectPosition = mCardStackView.getSelectPosition();
        final CardStackViewNew.ViewHolder preSelectViewHolder = mCardStackView.getViewHolder(preSelectPosition);
        if (preSelectViewHolder != null) {
            preSelectViewHolder.onItemExpand(false);
        }
        mCardStackView.setSelectPosition(position);
        itemExpandAnimatorSet(viewHolder, position);
        mSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardStackView.setScrollEnable(false);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_START, false);
                }
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_START, true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewHolder.onItemExpand(true);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_END, false);
                }
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_END, true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_CANCEL, false);
                }
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_CANCEL, true);
            }
        });
        mSet.start();
    }

    private void onItemCollapse(final CardStackViewNew.ViewHolder viewHolder) {
        itemCollapseAnimatorSet(viewHolder);
        mSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                viewHolder.onItemExpand(false);
                mCardStackView.setScrollEnable(true);
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_START, false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardStackView.setSelectPosition(CardStackViewNew.DEFAULT_SELECT_POSITION);
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_END, false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                viewHolder.onAnimationStateChange(CardStackViewNew.ANIMATION_STATE_CANCEL, false);
            }
        });
        mSet.start();
    }

    protected int getCollapseStartTop(int collapseShowItemCount) {
        return mCardStackView.getOverlapGapsCollapse()
                * (mCardStackView.getNumBottomShow() - collapseShowItemCount - (mCardStackView.getNumBottomShow() - (mCardStackView.getChildCount() - mCardStackView.getSelectPosition() > mCardStackView.getNumBottomShow()
                ? mCardStackView.getNumBottomShow()
                : mCardStackView.getChildCount() - mCardStackView.getSelectPosition() - 1)));
    }

    public int getDuration() {
        return mCardStackView.getDuration();
    }
}
