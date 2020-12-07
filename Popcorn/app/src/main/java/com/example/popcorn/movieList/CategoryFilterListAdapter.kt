package com.example.popcorn.movieList

import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.databinding.CategoryFilterListItemBinding
import com.example.popcorn.movie.MovieCategory

class CategoryFilterListAdapter(
    val onFilterAdded: (MovieCategory) -> Unit,
    val onFilterRemoved: (MovieCategory) -> Unit
) : RecyclerView.Adapter<CategoryFilterListAdapter.CategoryListViewHolder>() {

    class CategoryListViewHolder(val categoryFilterListItemBinding: CategoryFilterListItemBinding) :
        RecyclerView.ViewHolder(categoryFilterListItemBinding.root)

    private var data: List<CategoryFilter>? = null

    fun submitList(list: List<CategoryFilter>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            CategoryFilterListItemBinding.inflate(
                parent.context.getSystemService()!!,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val categoryFilter = data?.get(position) ?: return
        holder.categoryFilterListItemBinding.categoryItemCheckbox.isChecked = categoryFilter.checked
        holder.categoryFilterListItemBinding.categoryItemCheckbox.text =
            categoryFilter.category.displayName
        holder.categoryFilterListItemBinding.categoryItemCheckbox.setOnClickListener {
            if (holder.categoryFilterListItemBinding.categoryItemCheckbox.isChecked)
                onFilterAdded(categoryFilter.category)
            else onFilterRemoved(categoryFilter.category)

        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}