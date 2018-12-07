package com.mindgeek.myapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mindgeek.myapp.interfaces.IView
import com.mindgeek.myapp.presenters.SecurityActivityPresenter
import kotlinx.android.synthetic.main.activity_security2.*

class SecurityActivity : AppCompatActivity(), IView
{
    var presenter: SecurityActivityPresenter = SecurityActivityPresenter(this)

    override val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security2)

        presenter.updateModelFromState(savedInstanceState ?: intent.extras)
        verifyFingerprint.setOnClickListener { presenter.setPassword(123); presenter.verifySecureMode() }
        invalidFingerprint.setOnClickListener { presenter.setPassword(0); presenter.verifySecureMode() }
    }

    override fun onStart()
    {
        super.onStart()
        presenter.verifySecureMode()
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        super.onSaveInstanceState(outState)
        presenter.updateStateFromModel(outState)
    }
}
