package com.jpp.moviespreview.app.ui.sections.about

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.util.extentions.inflate
import kotlinx.android.synthetic.main.about_list_item.view.*

/**
 * Created by jpp on 1/19/18.
 */
class AboutActionsAdapter(private val items: List<AboutAction>,
                          private val listener: (AboutAction) -> Unit) : RecyclerView.Adapter<AboutActionsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindAction(items[position], listener)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.about_list_item))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindAction(action: AboutAction, listener: (AboutAction) -> Unit) {
            with(action) {
                itemView.about_title_text_view.text = title
                itemView.about_item_image_view.setImageResource(icon)
                itemView.setOnClickListener {
                    listener(action)
                }
            }
        }
    }
}