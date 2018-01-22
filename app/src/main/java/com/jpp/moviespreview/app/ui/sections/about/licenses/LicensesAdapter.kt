package com.jpp.moviespreview.app.ui.sections.about.licenses

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.util.extentions.inflate
import org.jetbrains.anko.find

/**
 * Created by jpp on 1/20/18.
 */
class LicensesAdapter(private val items: List<License>,
                      private val listener: (License) -> Unit) : RecyclerView.Adapter<LicensesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(android.R.layout.simple_list_item_1))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindLicense(items[position], listener)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindLicense(license: License, listener: (License) -> Unit) {
            val textView = itemView.find<TextView>(android.R.id.text1)
            textView.setTextAppearance(itemView.context, R.style.MoviesPreviewTextAppearanceInverse_Small)
            textView.text = license.name
            itemView.setOnClickListener { listener(license) }
        }

    }
}