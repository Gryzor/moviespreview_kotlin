package com.jpp.moviespreview.app.domain

/**
 * Defines the signature of a UseCase
 *
 * Created by jpp on 10/5/17.
 */
interface UseCase<Param, Response> {

    /**
     * Executes the use case: consists on receiving a Param and execute actions
     * with that param (or not) returning a Response that can be null (representing
     * an error)
     */
    fun execute(param: Param?): Response?
}