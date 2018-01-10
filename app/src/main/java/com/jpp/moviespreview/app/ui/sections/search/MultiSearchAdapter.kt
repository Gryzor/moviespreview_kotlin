package com.jpp.moviespreview.app.ui.sections.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.util.extentions.inflate
import com.jpp.moviespreview.app.util.extentions.loadCircularImageView
import com.jpp.moviespreview.app.util.extentions.setVisibleIf
import kotlinx.android.synthetic.main.multi_search_list_item.view.*

/**
 * Created by jpp on 1/9/18.
 */
class MultiSearchAdapter(private val searchResults: MutableList<MultiSearchResult> = mutableListOf())
    : RecyclerView.Adapter<MultiSearchAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindResult(searchResults[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.multi_search_list_item))

    override fun getItemCount() = searchResults.size

    fun appendResults(results: List<MultiSearchResult>) {
        val currentSize = itemCount
        searchResults.addAll(results)
        notifyItemRangeChanged(currentSize, itemCount)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindResult(result: MultiSearchResult) {
            with(result) {
                itemView.multi_search_title_text_view.text = name
                itemView.multi_search_chevron_image_view.setVisibleIf { hasDetails }
                itemView.multi_search_item_type_image_view.setImageResource(icon)
                itemView.multi_search_item_image_view.loadCircularImageView(imagePath, icon, icon)
            }
        }
    }

}