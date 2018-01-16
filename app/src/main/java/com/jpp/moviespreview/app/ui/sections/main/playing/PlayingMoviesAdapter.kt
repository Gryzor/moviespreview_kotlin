package com.jpp.moviespreview.app.ui.sections.main.playing

import android.support.v4.graphics.ColorUtils
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.util.extentions.inflate
import com.jpp.moviespreview.app.util.extentions.loadImageUrlWithCallback
import kotlinx.android.synthetic.main.movie_list_item.view.*

class PlayingMoviesAdapter(private val listener: (Movie, ImageView) -> Unit,
                           private val moviesList: MutableList<Movie> = mutableListOf()) : RecyclerView.Adapter<PlayingMoviesAdapter.ViewHolder>() {


    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindMovie(moviesList[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.movie_list_item))

    fun appendMovies(movies: List<Movie>) {
        val currentSize = itemCount
        moviesList.addAll(movies)
        notifyItemRangeChanged(currentSize, itemCount)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindMovie(movie: Movie, listener: (Movie, ImageView) -> Unit) {
            with(movie) {
                itemView.txt_movie_item_popularity.text = popularity.toString()
                itemView.txt_movie_item_vote_count.text = voteCount.toString()
                itemView.iv_movie_item.loadImageUrlWithCallback(movie.getPosterPath(), {
                    Palette.from(it).generate {
                        /*
                         * Use palette to generate a color, and use it as background
                         * of the bottom icons and texts.
                         */
                        val swatchColor = it.lightVibrantSwatch?.rgb ?: android.R.color.white
                        itemView.view_movie_item_bottom_background.setBackgroundColor(ColorUtils.setAlphaComponent(swatchColor, 180))
                    }
                })
                itemView.setOnClickListener { listener(movie, itemView.iv_movie_item) }
            }
        }
    }
}