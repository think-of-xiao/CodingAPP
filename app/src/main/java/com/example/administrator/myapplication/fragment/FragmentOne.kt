package com.example.administrator.myapplication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.myapplication.R

class FragmentOne: Fragment() {

    override fun onResume() {
        super.onResume()
        Log.w("FragmentOne", "onResume")
    }

    @SuppressLint("LongLogTag")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.w("FragmentOne -> onHiddenChanged", hidden.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one_layout, container, false)
    }

}