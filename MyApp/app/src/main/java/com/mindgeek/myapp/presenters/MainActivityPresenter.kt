package com.mindgeek.myapp.presenters

import android.os.Bundle
import com.mindgeek.myapp.models.MainActivityModel

class MainActivityPresenter
{
    private lateinit var model: MainActivityModel
    fun initializeModel(savedInstanceState: Bundle?)
    {
        if (savedInstanceState != null)
        {
            model.setParcelableObject(savedInstanceState)
        }
    }

    fun isModelInitialized(): Boolean = !model.password.isEmpty()

    fun verifySecurity()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateModel(outState: Bundle?) {


    }

}