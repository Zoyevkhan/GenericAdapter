package com.example.genericadapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genericadapter.databinding.FragmentChooseLayoutBinding
import com.example.genericadapter.databinding.FragmentChooseStateBinding
import com.example.genericadapter.model.Language
import com.example.genericadapter.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseStateFragment : BaseFragment<FragmentChooseStateBinding>(R.layout.fragment_choose_state) {
    lateinit var genericAdapter:CommonRecyclerViewAdapter<State>
    private lateinit var activity :MainActivity
    private val noteDatabase by lazy { NoteDatabase.getDatabase(activity).languageDatabase()}
    val viewmodel:PreferenceViewModel by activityViewModels<PreferenceViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun observeData() {
        lifecycleScope.launch(Dispatchers.IO) {
            var list=  noteDatabase.getAllLanguageSimple(false).map {
                State(it.title,it.isSelected)
            }
            genericAdapter.addItems(list)
        }

      /*  noteDatabase.getAllLanguage().observe(viewLifecycleOwner, Observer {
            it?.let {
                    preferenceList->
              var list=  preferenceList.map {
                    State(it.title,it.isSelected)
                }

            }
        })*/
       /* viewmodel.statePreferenceList.observe(viewLifecycleOwner, Observer { _preferenceList ->
            _preferenceList?.let {
                    preferenceList->
                genericAdapter.addItems(preferenceList)
            }
        })*/
    }

    override fun observeEvents() {
        lifecycleScope.launch{
            viewmodel.statePrefeneceEvents.collect{ UIEvent ->
                when(UIEvent){
                    is PreferenceViewModel.UIEvent.onLoading ->{
                        activity.showLoader(true)
                    }
                    is PreferenceViewModel.UIEvent.OnError ->{
                        activity.showLoader(false)
                        Toast.makeText(requireContext(),UIEvent.message,Toast.LENGTH_LONG).show()
                    }
                    is PreferenceViewModel.UIEvent.Success ->{
                        activity.showLoader(false)
                    }

                    else -> {}
                }

            }
        }
    }
    override fun initialize(){
        genericAdapter = CommonRecyclerViewAdapter(R.layout.item_state)
        binding.recylerview.adapter=genericAdapter
        binding.recylerview.layoutManager= GridLayoutManager(requireContext(),3)
        handleClickEvent()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity =context as MainActivity
    }

    fun handleClickEvent(){
        with(binding){
            backButton.setOnClickListener {
                activity.removeFragment()
            }
            skipButton.setOnClickListener {
                moveToNextFragment(true)
            }
            nextButton.setOnClickListener {
                moveToNextFragment()
            }
        }
    }
    fun moveToNextFragment(
        isSkipFragment:Boolean = false
    ){
        activity.replaceFragment(
            FragmentChooseCategory(),
            (requireView().parent as ViewGroup).id,
            true,
            isSkipFragment
        )
    }
}