package com.jpp.moviespreview.app.ui.detail.body

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.util.extentions.inflate
import kotlinx.android.synthetic.main.movie_details_genre_list_item.view.*


class MovieDetailsGenreAdapter(private val genres: List<MovieGenre>) : RecyclerView.Adapter<MovieDetailsGenreAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(genres[position])

    override fun getItemCount() = genres.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.movie_details_genre_list_item))


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(genre: MovieGenre) {
            with(genre) {
                itemView.genre_list_item_image_view.setImageResource(icon)
                itemView.genre_list_item_text_view.text = name
            }
        }

    }

}