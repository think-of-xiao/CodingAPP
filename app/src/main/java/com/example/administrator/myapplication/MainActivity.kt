package com.example.administrator.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.View
import com.example.administrator.myapplication.fragment.FragmentOne
import com.example.administrator.myapplication.fragment.FragmentTwo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var loginType: String = "sms"
    var one: FragmentOne = FragmentOne()
    var two: FragmentTwo = FragmentTwo()
    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun oneClick(view: View) {
        startActivity(Intent(this@MainActivity, CardStackViewActivity::class.java))
    }

    private fun initView() {
        rg_choice_login_way.setOnCheckedChangeListener { group, checkedId ->
            group.check(checkedId)
            when (checkedId) {
                R.id.rb_sms_login -> {
                    loginType = "sms"
                    selectorFragment(one)
                }
                R.id.rb_pws_login -> {
                    loginType = "pws"
                    selectorFragment(two)
                }
            }
        }
        if (TextUtils.equals(loginType, "sms")) {
            currentFragment = two
            rg_choice_login_way.postDelayed({ selectorFragment(one) }, 200)
        } else if (TextUtils.equals(loginType, "pws")) {
            currentFragment = one
            rg_choice_login_way.postDelayed({ selectorFragment(two) }, 200)
        }
    }

    @SuppressLint("LongLogTag")
    private fun selectorFragment(fragment: Fragment) {
        if (currentFragment != fragment) {
            if (fragment.isAdded) {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment)
                        .add(R.id.fl_content, fragment).show(fragment).commit()
            }
            currentFragment = fragment
        }
    }
}
