package com.mario.desafiojuno.util


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mario on 12/1/17.
 * Minhas Extensions
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().addToBackStack(null).commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun FragmentManager.replaceFragment(fragment: Fragment, frameId: Int) {
    inTransaction { replace(frameId, fragment) }
}

fun Date.toDate() = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)


