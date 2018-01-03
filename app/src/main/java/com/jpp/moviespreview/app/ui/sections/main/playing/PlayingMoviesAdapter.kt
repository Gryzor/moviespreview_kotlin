package com.jpp.moviespreview.app.ui.sections.main.playing

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.viewpager.ImageViewPagerAdapter
import com.jpp.moviespreview.app.util.extentions.getPositionForElement
import com.jpp.moviespreview.app.util.extentions.inflate
import com.jpp.moviespreview.app.util.extentions.loadImageUrl
import com.jpp.moviespreview.app.util.extentions.pageChangeUpdate
import kotlinx.android.synthetic.main.movie_list_item.view.*

class PlayingMoviesAdapter(private val listener: (Movie, ViewPager) -> Unit,
                           private val onPageChanged: (Movie, Int) -> Unit,
                           private val moviesList: MutableList<Movie> = mutableListOf()) : RecyclerView.Adapter<PlayingMoviesAdapter.ViewHolder>() {


    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindMovie(moviesList[position], listener, onPageChanged)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.movie_list_item))

    fun appendMovies(movies: List<Movie>) {
        val currentSize = itemCount
        moviesList.addAll(movies)
        notifyItemRangeChanged(currentSize, itemCount)
    }

    fun refreshMovie(movie: Movie) {
        val position = moviesList.getPositionForElement { current: Movie -> current.id == movie.id }
        notifyItemChanged(position)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindMovie(movie: Movie, listener: (Movie, ViewPager) -> Unit, onPageChanged: (Movie, Int) -> Unit) {
            with(movie) {
                itemView.txt_movie_item_title.text = title
                itemView.txt_movie_item_popularity.text = popularity.toString()
                itemView.txt_movie_item_vote_count.text = voteCount.toString()
                itemView.vp_movie_item_poster.adapter = ImageViewPagerAdapter(movie.images.size, { imageView: ImageView, position: Int ->
                    imageView.loadImageUrl(movie.images[position])
                    imageView.setOnClickListener { listener(movie, itemView.vp_movie_item_poster) } // handle clicks on the ViewPager
                })
                itemView.vp_movie_item_poster.pageChangeUpdate { position: Int -> onPageChanged(movie, position) }
                itemView.vp_movie_item_poster.setCurrentItem(movie.currentImageShown, false)
                itemView.vp_movie_item_tab_indicator.setupWithViewPager(itemView.vp_movie_item_poster, true)
                itemView.setOnClickListener { listener(movie, itemView.vp_movie_item_poster) }
            }
        }
    }
}