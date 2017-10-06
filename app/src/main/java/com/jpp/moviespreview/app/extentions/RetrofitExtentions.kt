package com.jpp.moviespreview.app.extentions

import retrofit2.Call


/**
 * Extension function to Call that unwraps the result T of the call
 * by applying a lambda expression
 */
inline fun <T, U> Call<T>.unwrapCall(f: (T) -> U): U {
    return f(execute().body())
}