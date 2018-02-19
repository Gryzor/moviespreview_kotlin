package com.jpp.moviespreview.app.ui.sections.main.movies

/**
 * Created by jpp on 2/19/18.
 */
class MoviesPresenterImpl(private val moviesInContextProvider: MoviesInContextProvider,
                          private var interactor: MoviesPresenterInteractor) : MoviesPresenter {


    private lateinit var moviesView: MoviesView

    override fun linkView(moviesView: MoviesView) {
        this.moviesView = moviesView
        with(moviesInContextProvider) {
            when (getAllMoviePages().size) {
                0 -> getNextMoviePage()
                else -> getAllMoviePages().forEach { moviesView.showMoviePage(it) }
            }
        }
    }


    private fun getNextMoviePage() {

    }

}