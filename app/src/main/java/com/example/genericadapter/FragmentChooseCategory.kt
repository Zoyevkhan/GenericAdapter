package com.example.genericadapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.genericadapter.databinding.FragmentChooseCategoryBinding
import com.example.genericadapter.databinding.FragmentChooseStateBinding
import com.example.genericadapter.model.Category
import com.example.genericadapter.model.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentChooseCategory: BaseFragment<FragmentChooseCategoryBinding>(R.layout.fragment_choose_category) {
    private var selectedLanguageList: MutableList<Category> = mutableListOf()
    lateinit var genericAdapter:CommonRecyclerViewAdapter<Category>
    private lateinit var activity :MainActivity
    val viewmodel:PreferenceViewModel by activityViewModels<PreferenceViewModel>()


    override fun observeEvents() {
        lifecycleScope.launch {
            viewmodel.categoryPrefeneceEvents.collect{ UIEvent ->
                when(UIEvent){
                    is PreferenceViewModel.UIEvent.onLoading ->{
                        activity.showLoader(true)
                    }
                    is PreferenceViewModel.UIEvent.OnError -> {
                        activity.showLoader(false)
                        Toast.makeText(requireContext(),UIEvent.message, Toast.LENGTH_LONG).show()
                    }
                    is PreferenceViewModel.UIEvent.Success -> {
                        activity.showLoader(false)
                    }
                    else -> {

                    }
                }

            }
        }

    }

    override fun observeData() {
        viewmodel.categoryPreferenceList.observe(viewLifecycleOwner, Observer { _preferenceList ->
            _preferenceList?.let {
                    preferenceList->
                genericAdapter.addItems(preferenceList)
            }
        })
    }
    override fun initialize(){
         genericAdapter = CommonRecyclerViewAdapter(R.layout.item_category)
        binding.recylerview.adapter=genericAdapter
        binding.recylerview.layoutManager= GridLayoutManager(requireContext(),2)
        handleClickEvent()
    }
    private fun handleClickEvent() {
        with(binding) {
            backButton.setOnClickListener {
                activity.removeFragment()
            }
            nextButton.setOnClickListener {
                navigateToHomeActivity()
            }
            skipButton.setOnClickListener {
                navigateToHomeActivity()
            }
        }
    }

    fun navigateToHomeActivity(
    ){
        startActivity(Intent(activity,HomeActivity::class.java))
       activity.finish()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity =context as MainActivity
    }


}