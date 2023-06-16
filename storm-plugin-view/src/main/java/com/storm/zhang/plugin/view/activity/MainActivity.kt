package com.storm.zhang.plugin.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.storm.zhang.plugin.view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG   = "MainActivity"
    }

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }
}

