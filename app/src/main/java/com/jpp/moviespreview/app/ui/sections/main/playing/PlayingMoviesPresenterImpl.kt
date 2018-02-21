package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
 * Presenter implementation for the playing movies in theater section.
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: ApplicationMoviesContext,
                                 private val interactorDelegate: PlayingMoviesPresenterController,
                                 private val playingMoviesUseCase: UseCase<PageParam, DomainMoviePage>,
                                 private val mapper: DomainToUiDataMapper) : PlayingMoviesPresenter {


    private lateinit var playingMoviesView: PlayingMoviesView
    private var targetScreenWidth: Int? = null

    // function to assign a target screen width
    private val assignTargetScreenWidth = { value: Int ->
        targetScreenWidth = value
        targetScreenWidth!!
    }

    override fun linkView(view: PlayingMoviesView) {
        playingMoviesView = view
        loadOrRetrieveMovies()
    }


    /**
     * Verifies if there are pages loaded into the context. If there are, loads those
     * pages into the screen. If not, it attempts to retrieve the new pages.
     */
    private fun loadOrRetrieveMovies() {
        with(moviesContext) {
            if (hasMoviePages()) {
                getAllMoviePages().forEach({
                    playingMoviesView.showMoviePage(it)
                })
            } else {
                getNextMoviePage()
            }
        }
    }


    override fun getNextMoviePage() {
        with(moviesContext) {
            if (isConfigCompleted()) {
                if (interactorDelegate.isIdle()) {
                    createNextUseCaseParam({ executeUseCase(it) })
                }
            } else {
                playingMoviesView.backToSplashScreen()
            }
        }
    }

    override fun onMovieSelected(movie: Movie) {
        moviesContext.selectedMovie = movie
    }


    /**
     * Creates the [PageParam] that is going to be used for the next
     * use case execution. If the scrolling has reached the last possible position,
     * it asks the view to show the end of page and returns null.
     */
    fun createNextUseCaseParam(manager: (PageParam) -> Unit) {
        with(moviesContext) {
            interactorDelegate.controlPagination(
                    { getAllMoviePages() },
                    { playingMoviesView.showEndOfPaging() },
                    { manager(PageParam(it, mapper.convertUiGenresToDomainGenres(movieGenres!!))) }
            )
        }
    }


    /**
     * If the provided [moviePage] is not null, then this method takes care of
     * converting the domain classes to ui classes, set the new page in the context (if possible) and
     * ask to the view to show the new page.
     * If [moviePage] is null, then it asks to the view to show the error.
     */
    private fun processMoviesPage(moviePage: DomainMoviePage?) {
        moviePage?.let {
            val selectedImageConfig = interactorDelegate.findPosterImageConfigurationForWidth(moviesContext.posterImageConfig!!, getImagesWidthObjective())
            val convertedMoviePage = mapper.convertDomainMoviePageToUiMoviePage(it, selectedImageConfig, moviesContext.movieGenres!!)
            moviesContext.addMoviePage(convertedMoviePage)
            playingMoviesView.showMoviePage(convertedMoviePage)
        } ?: processError()
    }


    /**
     * Process the detected error by showing an internet connection error
     * or a unexpected error message.
     */
    private fun processError() {
        with(interactorDelegate) {
            if (isConnectedToNetwork()) {
                playingMoviesView.showUnexpectedError()
            } else {
                playingMoviesView.showNotConnectedToNetwork()
            }
        }
    }


    /**
     * Finds the proper width for the [ImageConfiguration] to be used by the imagesPresenter.
     * The [mapper] will create and set the proper image URL for the movies that are in a [MoviePage].
     * In order to do that, we need to target a given screen width that will be the one that defines
     * the image to retrieve from the server.
     */
    private fun getImagesWidthObjective(): Int {
        return targetScreenWidth ?:
                assignTargetScreenWidth(playingMoviesView.getScreenWidth())
    }


    /**
     * Executes the use case to retrieve the movie page.
     */
    private fun executeUseCase(param: PageParam) {
        interactorDelegate.executeBackgroundJob(
                {
                    showLoadingIfNeeded()
                    playingMoviesUseCase.execute(param)
                },
                {
                    processMoviesPage(it)
                }
        )
    }

    /**
     * Asks the view to show the initial loading view if it's the initial load.
     */
    private fun showLoadingIfNeeded() {
        with(moviesContext) {
            if (getAllMoviePages().isEmpty()) {
                playingMoviesView.showInitialLoading()
            }
        }
    }

}