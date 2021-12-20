package com.smartphoneprogamming.afinal.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.tasks.Task
import com.smartphoneprogamming.afinal.R

class SettingActivity : AppCompatActivity() {
    val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        ShowFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_pref, PreferenceActivity())
//            .commit()
    }
//    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
//        // Instantiate the new Fragment
//        val args = pref.extras
//        val fragment = supportFragmentManager.fragmentFactory.instantiate(
//            classLoader,
//            pref.fragment)
//        fragment.arguments = args
//        fragment.setTargetFragment(caller, 0)
//        // Replace the existing Fragment with the new Fragment
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_pref, PreferenceActivity())
//            .addToBackStack(null)
//            .commit()
//        return true
//    }
fun ShowFragment(){
    val transaction = manager.beginTransaction()
    val fragment = PreferenceActivity()
    transaction.replace(R.id.main_pref, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
}