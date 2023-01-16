package com.example.genericadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.genericadapter.databinding.ItemCategoryBinding
import com.example.genericadapter.databinding.ItemLanguageBinding
import com.example.genericadapter.databinding.ItemStateBinding
import com.example.genericadapter.model.Category
import com.example.genericadapter.model.Language
import com.example.genericadapter.model.State
import kotlinx.coroutines.currentCoroutineContext

class CommonRecyclerViewAdapter <T : Any>(@LayoutRes val layoutId: Int) : RecyclerView.Adapter<CommonRecyclerViewAdapter.GenericViewHolder>()
{

    private val containerList = mutableListOf<T>()
    fun addItems(items: List<T>) {
        this.containerList.clear()
        this.containerList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenericViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent, false))
    override fun getItemCount(): Int = containerList.size

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        var itemContainer = containerList[position]
        holder.binding.setVariable(BR.container, itemContainer)
        when(holder.binding){
           is ItemLanguageBinding ->{
               handleLanguageUI(holder.binding,itemContainer as Language,position)
           }
            is ItemStateBinding->{
                handleStateUI(holder.binding,itemContainer as State,position)
            }
            is ItemCategoryBinding->{
                handleCategoryUI(holder.binding,itemContainer as Category,position)
            }
        }
    }

    fun handleLanguageUI(binding: ItemLanguageBinding,language:Language,position: Int){
        with(binding) {
            selectionLogo.setImageResource(if(language.isSelected) R.drawable.language_menu_selected_icon else R.drawable.language_menu_unselected)
            selectionLogo.setOnClickListener {
                language.isSelected = !language.isSelected
                containerList.forEachIndexed {
                        index, item ->
                    if((item as Language).isSelected && index!=position){
                        (item as Language).isSelected=false
                        notifyItemChanged(index)
                    }
                }
                notifyItemChanged(position)
            }
        }
    }
    fun handleStateUI(binding: ItemStateBinding,state:State,position: Int){
        with(binding) {
            selectionLogo.setImageResource(if(!state.isSelected) R.drawable.state_menu_unselected else R.drawable.language_menu_selected_icon)
            parentLl.setOnClickListener {
                state.isSelected = !state.isSelected
                notifyItemChanged(position)
            }
        }
    }

    fun handleCategoryUI(binding: ItemCategoryBinding,category: Category,position: Int){
        with(binding) {
            selectionLogo.setImageResource(if(!category.isSelected) R.drawable.state_menu_unselected else R.drawable.language_menu_selected_icon)
            selectionLogo.setOnClickListener {
                category.isSelected = !category.isSelected
                notifyItemChanged(position)
            }
        }
    }
    class GenericViewHolder( val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


}