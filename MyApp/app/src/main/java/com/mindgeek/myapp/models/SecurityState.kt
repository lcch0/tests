package com.mindgeek.myapp.models

enum class SecurityState
{
    SecurityOnPassNotSet,//default value - let's consider security on all the time, just in case
    SecurityOnPassSet,
    SecurityOff,//if it's off, pass set doesn't make sense
}