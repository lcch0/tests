package com.dss.manetestapp.transfos

import androidx.lifecycle.*
import kotlin.random.Random

class TransfoViewModel : ViewModel()
{
    val mainProp: MutableLiveData<Int> = MutableLiveData()
    val secondProp: LiveData<String> = Transformations.map(mainProp){src -> "This is second value ${src?:-1}"}
    val thirdProp: MutableLiveData<String> = MutableLiveData()
    val forthProp: MutableLiveData<String> = MutableLiveData()
    var fifthProp: LiveData<String> = MutableLiveData()

    init
    {
        mainProp.observeForever {
            thirdProp.value = "This is third value ${it?:0}"
        }

        val result: MediatorLiveData<String> = MediatorLiveData()
        result.addSource(mainProp) { x -> result.setValue("This is fifth value ${x ?: 0}") }

        fifthProp = result
    }

    fun loadData()
    {
        mainProp.value = Random.nextInt()
        forthProp.value = "This is forth value ${mainProp.value}"
    }
}