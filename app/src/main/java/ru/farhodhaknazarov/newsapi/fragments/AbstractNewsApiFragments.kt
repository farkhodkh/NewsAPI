package ru.farhodhaknazarov.newsapi.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class AbstractNewsApiFragments: Fragment(){
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}