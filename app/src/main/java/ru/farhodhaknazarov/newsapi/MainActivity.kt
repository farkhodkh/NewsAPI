package ru.farhodhaknazarov.newsapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import ru.farhodhaknazarov.newsapi.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity() {
    lateinit var presenter: MainActivityPresenter
    lateinit var pager: ViewPager

    companion object{
        private lateinit var instance: MainActivity

        fun setInstance(activity: MainActivity){
            instance = activity
        }

        fun getInstance(): MainActivity{
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById(R.id.pager)

        presenter = MainActivityPresenter(this)
        MainActivityPresenter.setContext(this.applicationContext)
        presenter.prepareView()

        MainActivity.setInstance(this)
    }
}
