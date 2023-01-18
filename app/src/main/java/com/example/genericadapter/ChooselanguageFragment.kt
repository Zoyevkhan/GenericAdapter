package com.example.genericadapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genericadapter.databinding.FragmentChooseLayoutBinding
import com.example.genericadapter.model.Language
import kotlinx.coroutines.launch

class ChooselanguageFragment : BaseFragment<FragmentChooseLayoutBinding>(R.layout.fragment_choose_layout) {
    lateinit var activity:MainActivity
     lateinit var genericAdapter:CommonRecyclerViewAdapter<Language>
    private var selectedLanguageList:MutableList<Language> = mutableListOf()
    val viewmodel:PreferenceViewModel by activityViewModels<PreferenceViewModel>()


    override fun observeData() {
        viewmodel.languagePreferenceList.observe(viewLifecycleOwner, Observer { preferenceList ->
            preferenceList?.let {
                    preferenceList->
                genericAdapter.addItems(preferenceList)
            }
        })
    }

    override fun observeEvents() {
        lifecycleScope.launch{
            viewmodel.languagePrefeneceEvents.collect{ UIEvent ->
                when(UIEvent){
                    is PreferenceViewModel.UIEvent.onLoading ->{
                        shoowLoader(true)
                    }
                    is PreferenceViewModel.UIEvent.OnError ->{
                        shoowLoader(false)
                        Toast.makeText(requireContext(),UIEvent.message,Toast.LENGTH_LONG).show()
                    }
                    is PreferenceViewModel.UIEvent.Success ->{
                        shoowLoader(false)
                    }
                    else -> {

                    }
                }

            }
        }

    }

    private fun shoowLoader(shouldShow: Boolean) {
        activity.showLoader(shouldShow)

    }


    override fun initialize() {
        genericAdapter = CommonRecyclerViewAdapter<Language>(R.layout.item_language)
        selectedLanguageList = mutableListOf()
        binding.recylerview.adapter=genericAdapter
        binding.recylerview.layoutManager=LinearLayoutManager(requireContext())
        handleCickEvent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity=context as MainActivity
    }
    fun handleCickEvent(){
        binding.nextButton.setOnClickListener {
            viewmodel.addIntoDB()
            activity.replaceFragment(
                ChooseStateFragment(),
                (requireView().parent as ViewGroup).id, true
            )
        }
    }


}