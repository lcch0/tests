package com.mindgeek.myapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mindgeek.myapp.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity()
{
    private var presenter: MainActivityPresenter = MainActivityPresenter();

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.initializeModel(savedInstanceState)
        presenter.verifySecurity()
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        super.onSaveInstanceState(outState)
        presenter.updateModel(outState)
    }
}
