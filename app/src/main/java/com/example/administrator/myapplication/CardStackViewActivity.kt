package com.example.administrator.myapplication

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_stack_view_layout.*
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.example.administrator.myapplication.adapter.TestStackAdapter
import com.example.administrator.myapplication.view.cardstackviewlib.AllMoveDownAnimatorAdapter
import com.example.administrator.myapplication.view.cardstackviewlib.CardStackView
import com.example.administrator.myapplication.view.cardstackviewlib.UpDownAnimatorAdapter
import com.example.administrator.myapplication.view.cardstackviewlib.UpDownStackAnimatorAdapter

class CardStackViewActivity : AppCompatActivity(), CardStackView.ItemExpendListener {
    private var DATAS = arrayOf(R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
            R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12, R.color.color_13/*,
            R.color.color_14, R.color.color_15, R.color.color_16, R.color.color_17, R.color.color_18,
            R.color.color_19, R.color.color_20, R.color.color_21, R.color.color_22, R.color.color_23,
            R.color.color_24, R.color.color_25, R.color.color_26*/)
    private var mTestStackAdapter: TestStackAdapter? = null
    private var cardStackViewItemExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_stack_view_layout)
        stackView.setAnimatorAdapter(AllMoveDownAnimatorAdapter(stackView))
        stackView.itemExpendListener = this
        mTestStackAdapter = TestStackAdapter(this)
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
            R.id.menu_all_down -> stackView.setAnimatorAdapter(AllMoveDownAnimatorAdapter(stackView))
            R.id.menu_up_down -> stackView.setAnimatorAdapter(UpDownAnimatorAdapter(stackView))
            R.id.menu_up_down_stack -> stackView.setAnimatorAdapter(UpDownStackAnimatorAdapter(stackView))
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
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if ((ev?.action == MotionEvent.ACTION_MOVE) && cardStackViewItemExpand){
//            return true
//        }
//        return super.dispatchTouchEvent(ev)
//    }

    override fun onItemExpend(expend: Boolean) {
        cardStackViewItemExpand = expend
    }

}