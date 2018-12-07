package com.mindgeek.myapp.interfaces

import com.mindgeek.myapp.models.ActivityModel

interface IPresenter
{
    /**
     * lunches the activity the current presenter is bound to
     */
    fun launchActivity(view: IView, model: ActivityModel)
}