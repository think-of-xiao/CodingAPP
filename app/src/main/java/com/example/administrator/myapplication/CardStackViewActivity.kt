package com.example.administrator.myapplication

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.administrator.myapplication.adapter.TestStackAdapter
import com.loopeer.cardstack.CardStackView
import kotlinx.android.synthetic.main.activity_second_layout.*
import com.loopeer.cardstack.UpDownStackAnimatorAdapter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.loopeer.cardstack.UpDownAnimatorAdapter
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter

class CardStackViewActivity : AppCompatActivity(), CardStackView.ItemExpendListener {
    private var DATAS = arrayOf(R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
            R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12, R.color.color_13,
            R.color.color_14, R.color.color_15, R.color.color_16, R.color.color_17, R.color.color_18,
            R.color.color_19, R.color.color_20, R.color.color_21, R.color.color_22, R.color.color_23,
            R.color.color_24, R.color.color_25, R.color.color_26)
    private var mTestStackAdapter: TestStackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_layout)
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

    override fun onItemExpend(expend: Boolean) {
        button_container?.visibility = if (expend) View.VISIBLE else View.GONE
    }
}