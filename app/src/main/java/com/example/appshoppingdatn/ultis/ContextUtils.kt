package com.example.appshoppingdatn.ultis

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import android.util.Log
import java.util.Locale

class ContextUtils(baseContext : Context) : ContextWrapper(baseContext) {
    companion object{
        var language = "en"

        fun updateLocale(context: Context,localeToSwith:Locale):ContextWrapper{
            var newContext = context
            val resource = context.resources
            val config = resource.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                val localeList = LocaleList(localeToSwith)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                Log.d("log1","1")
            }else{
                config.locale = localeToSwith
                Log.d("log1","2")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
               newContext = context.createConfigurationContext(config)
                Log.d("log1","3")
            }else{
                resource.updateConfiguration(config,resource.displayMetrics)
                Log.d("log1","4")
            }
            return ContextUtils(newContext)
        }
    }
}