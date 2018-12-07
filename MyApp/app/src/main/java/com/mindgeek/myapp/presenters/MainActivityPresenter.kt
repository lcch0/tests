package com.mindgeek.myapp.presenters

import android.content.Intent
import android.os.Bundle
import com.mindgeek.myapp.MainActivity
import com.mindgeek.myapp.interfaces.IView
import com.mindgeek.myapp.interfaces.IPresenter
import com.mindgeek.myapp.models.ActivityModel
import com.mindgeek.myapp.models.SecurityState

/**
 * presenter should have a reference to its view
 */
class MainActivityPresenter(private val view: IView) : IPresenter
{
    private var model: ActivityModel = ActivityModel()

    var secureMode
        get() = model.secureModeEnabled
        set(value) { model.secureModeEnabled = value }

    var password
        get() = model.password
        set(value) {model.password = value}

    /**
     * this method lunches own MainActivity activity
     */
    override fun launchActivity(view: IView, model: ActivityModel)
    {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.putExtra(model.key, model.getParcelableObject())
        view.context.startActivity(intent)
    }

    fun updateModelFromState(state: Bundle?)
    {
        if (state != null)
        {
            model.loadFromBundle(state)
        }
    }

    fun updateStateFromModel(state: Bundle?)
    {
        if (state != null)
        {
            model.saveToBundle(state)
        }
    }

    fun verifySecureMode()
    {
        when(model.getSecurityState())
        {
            SecurityState.SecurityOff          -> return//nothing to do
            SecurityState.SecurityOnPassSet    -> return//nothing to do
            SecurityState.SecurityOnPassNotSet -> SecurityActivityPresenter(view).launchActivity(view, model)
        }
    }
}