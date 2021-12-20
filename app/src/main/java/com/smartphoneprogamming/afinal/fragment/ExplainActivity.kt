package com.smartphoneprogamming.afinal.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.smartphoneprogamming.afinal.R
import kotlinx.android.synthetic.main.activity_explain.*


class ExplainActivity : AppCompatActivity() {
    private val adapter by lazy { ViewPagerAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explain)
        viewPager_main.adapter = ExplainActivity@adapter

    }
}