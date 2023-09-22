package com.medkissi.contactmanagergroupe1

import android.app.Application

class ContactApp:Application() {

    init {
        app = this
    }

    companion object{
        lateinit var  app:Application
        fun getAppContext() = app.applicationContext
    }
}