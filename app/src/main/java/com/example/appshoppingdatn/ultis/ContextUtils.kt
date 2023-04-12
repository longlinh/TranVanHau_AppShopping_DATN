package com.example.appshoppingdatn.ultis

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
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
            }else{
                config.locale = localeToSwith
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
               newContext = context.createConfigurationContext(config)
            }else{
                resource.updateConfiguration(config,resource.displayMetrics)
            }
            return ContextUtils(newContext)
        }
    }
}