package com.mindgeek.myapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mindgeek.myapp.interfaces.IView
import com.mindgeek.myapp.presenters.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IView
{
    override val context: Context
        get() = this

    private var presenter: MainActivityPresenter = MainActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.updateModelFromState(savedInstanceState ?: intent.extras)

        enableSecureMode.setOnClickListener { presenter.secureMode = enableSecureMode.isChecked }
        button.setOnClickListener { presenter.password = editText.text.toString().toIntOrNull() ?: 0 }
    }

    override fun onStart()
    {
        super.onStart()
        presenter.verifySecureMode()

        enableSecureMode.isChecked = presenter.secureMode
        editText.text.clear()
        editText.text.insert(0, presenter.password.toString())
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        presenter.updateStateFromModel(outState)
        super.onSaveInstanceState(outState)
    }
}
