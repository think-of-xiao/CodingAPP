package com.example.administrator.myapplication.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.myapplication.CardStackViewActivityNew
import com.example.administrator.myapplication.R
import kotlinx.android.synthetic.main.fragment_two_layout.*

class FragmentTwo: Fragment() {

    override fun onResume() {
        super.onResume()
        Log.w("FragmentTwo", "onResume")
    }

    @SuppressLint("LongLogTag")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.w("FragmentTwo -> onHiddenChanged", hidden.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_two_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_stack_view_new.setOnClickListener {
            startActivity(Intent(activity, CardStackViewActivityNew::class.java))
        }
    }
}