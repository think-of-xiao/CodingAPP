package com.example.administrator.myapplication

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.administrator.myapplication.adapter.TestStackAdapterNew
import kotlinx.android.synthetic.main.activity_second_layout.*
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.example.administrator.myapplication.view.cardstackviewlibNew.AllMoveDownAnimatorAdapterNew
import com.example.administrator.myapplication.view.cardstackviewlibNew.CardStackViewNew
import com.example.administrator.myapplication.view.cardstackviewlibNew.UpDownAnimatorAdapterNew
import com.example.administrator.myapplication.view.cardstackviewlibNew.UpDownStackAnimatorAdapterNew

class CardStackViewActivityNew : AppCompatActivity(), CardStackViewNew.ItemExpendListener {
    private var DATAS = arrayOf(R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4/*, R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
            R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12, R.color.color_13,
            R.color.color_14, R.color.color_15, R.color.color_16, R.color.color_17, R.color.color_18,
            R.color.color_19, R.color.color_20, R.color.color_21, R.color.color_22, R.color.color_23,
            R.color.color_24, R.color.color_25, R.color.color_26*/)
    private var mTestStackAdapter: TestStackAdapterNew? = null
    private var cardStackViewItemExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_layout)
        stackView.setAnimatorAdapter(AllMoveDownAnimatorAdapterNew(stackView))
        stackView.itemExpendListener = this
        mTestStackAdapter = TestStackAdapterNew(this)
        stackView.setAdapter(mTestStackAdapter)
        Handler().postDelayed(
                { mTestStackAdapter!!.updateData(DATAS.asList()) }, 200
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_all_down -> stackView.setAnimatorAdapter(AllMoveDownAnimatorAdapterNew(stackView))
            R.id.menu_up_down -> stackView.setAnimatorAdapter(UpDownAnimatorAdapterNew(stackView))
            R.id.menu_up_down_stack -> stackView.setAnimatorAdapter(UpDownStackAnimatorAdapterNew(stackView))
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPreClick(view: View) {
        stackView.pre()
    }

    fun onNextClick(view: View) {
        stackView.next()
    }

    /**
     * 控制当cardStackView点击item项展开后滑动事件activity的rootView自己处理，
     * 不分发给NestedScrollView，以达到屏幕不可滑的效果
     * */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if ((ev?.action == MotionEvent.ACTION_MOVE) && cardStackViewItemExpand){
            return true
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onItemExpend(expend: Boolean) {
        cardStackViewItemExpand = expend

        updateCardItemVisible(expend)

        button_top_container?.visibility = if (expend) View.GONE else View.VISIBLE
        button_bottom_container?.visibility = if (expend) View.GONE else View.VISIBLE
        if (expend) {
            nsv.fling(0) //解决NestedScrollView调用smoothScrollTo回不到顶部的问题
            nsv.smoothScrollTo(0, 0)
        }
        Log.w("---", "stackView的高度------------》${stackView.measuredHeight}")
        Log.w("---", "stackView的高度by getShowHeight------------》${stackView.showHeight}")
        Log.w("---", "stackView的child高度------------》${stackView.getChildAt(0).measuredHeight}")
    }

    /**
     * 更新展开时某一项itemView头上的向上箭头显隐状态
     */
    private fun updateCardItemVisible(expend: Boolean) {
        for (num in 0 until stackView.childCount) {
            stackView.getChildAt(num).findViewById<ImageView>(R.id.item_top_iv).visibility = View.GONE
        }
        val selectPosition = stackView.selectPosition
        if (expend && stackView.childCount >= 2 && selectPosition != stackView.childCount - 1) {
            stackView.getChildAt(selectPosition + 1).findViewById<ImageView>(R.id.item_top_iv).visibility = View.VISIBLE
        } else if (expend && stackView.childCount >= 2 && selectPosition == stackView.childCount - 1) {
            stackView.getChildAt(selectPosition - 1).findViewById<ImageView>(R.id.item_top_iv).visibility = View.VISIBLE
        }
    }
}