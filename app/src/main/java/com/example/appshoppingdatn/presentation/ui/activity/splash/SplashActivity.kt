package com.example.appshoppingdatn.presentation.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.database.SQLiteHelper
import com.example.appshoppingdatn.databinding.ActivitySplashBinding
import com.example.appshoppingdatn.presentation.ui.activity.login.LoginActivity
import com.example.appshoppingdatn.presentation.ui.base.activity.BaseActivity


class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var sqliteHelper : SQLiteHelper?=null

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_splash
    }

    override fun initControls(savedInstanceState: Bundle?) {
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation)
        mBinding.appLogo.startAnimation(logoAnimation)
        mBinding.appText.startAnimation(textAnimation)
        val intent = Intent(this, LoginActivity::class.java)
        val thread: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(intent)
                    finish()
                }
            }
        }
        thread.start()
        createTableSQLite()
    }

    private fun createTableSQLite() {
        sqliteHelper = SQLiteHelper(this,"Shopping1.db",null,2)
        sqliteHelper!!.QueryData("CREATE TABLE IF NOT EXISTS FAVORITE1 (Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdAccount VARCHAR(100)," +
                "IdSP INTEGER," +
                "Image NVARCHAR(100)," +
                "NameSP NVARCHAR(100)," +
                "PriceNow FLOAT,"+
                "PriceOld FLOAT,"+
                "Discription NVARCHAR(100),"+
                "Type NVARCHAR(50),"+
                "Selled INTEGER ,"+
                "StatusFav INTEGER ," +
                "CheckFav NVARCHAR(50),"+
                "Identifier INTEGER)")
    }


}