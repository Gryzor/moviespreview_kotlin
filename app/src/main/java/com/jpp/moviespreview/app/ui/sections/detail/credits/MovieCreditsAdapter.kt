package com.jpp.moviespreview.app.ui.sections.detail.credits

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.CreditPerson
import com.jpp.moviespreview.app.util.extentions.inflate
import com.jpp.moviespreview.app.util.extentions.loadCircularImageView
import kotlinx.android.synthetic.main.credits_list_item.view.*

/**
 * Created by jpp on 12/21/17.
 */
class MovieCreditsAdapter(private val credits: List<CreditPerson>) : RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.credits_list_item))

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binCredit(credits[position])


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun binCredit(credit: CreditPerson) {
            with(credit) {
                itemView.credit_item_text_view_1.text = title
                itemView.credit_item_text_view_2.text = subTitle
                itemView.credit_item_image_view.loadCircularImageView(profilePath)
            }
        }

    }

}