package com.mindgeek.myapp.presenters

import android.content.Intent
import android.os.Bundle
import com.mindgeek.myapp.interfaces.IView
import com.mindgeek.myapp.SecurityActivity
import com.mindgeek.myapp.interfaces.IPresenter
import com.mindgeek.myapp.models.ActivityModel
import com.mindgeek.myapp.models.SecurityState

class SecurityActivityPresenter(private val view: IView) : IPresenter
{
    private var model: ActivityModel = ActivityModel()

    override fun launchActivity(view: IView, model: ActivityModel)
    {
        val intent = Intent(view.context, SecurityActivity::class.java)
        intent.putExtra(model.key, model.getParcelableObject())
        view.context.startActivity(intent)
    }

    fun updateModelFromState(savedInstanceState: Bundle?)
    {
        if (savedInstanceState != null)
        {
            model.loadFromBundle(savedInstanceState)
        }
    }

    fun updateStateFromModel(outState: Bundle?)
    {
        if (outState != null)
        {
            model.saveToBundle(outState)
        }
    }

    fun verifySecureMode()
    {
        when(model.getSecurityState())
        {
            SecurityState.SecurityOff          -> return//nothing to do
            SecurityState.SecurityOnPassSet    -> MainActivityPresenter(view).launchActivity(view, model)
            SecurityState.SecurityOnPassNotSet -> return
        }
    }

    fun setPassword(password: Int)
    {
        model.password = password
    }
}
