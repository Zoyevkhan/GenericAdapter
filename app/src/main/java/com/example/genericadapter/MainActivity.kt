package com.example.genericadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.genericadapter.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val viewmodel:PreferenceViewModel by viewModels<PreferenceViewModel>()
    val progress:Progress by lazy {
        Progress(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.root.setOnClickListener {
            viewmodel.callPreferenceApi()
            if(savedInstanceState==null){
                addFragment(ChooselanguageFragment(),binding.mainContainer.id)
            }
        }
        /*selectedLanguageList =ArrayList(preferenceList.map { it.copy() })*/
    }
    fun showLoader(shouldShow:Boolean){
        if(shouldShow){
            progress.show()
        }else{
            progress.hide()
        }
    }
}