package ru.farhodhaknazarov.newsapi.repo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.farhodhaknazarov.newsapi.repo.entities.Articles
import ru.farhodhaknazarov.newsapi.repo.entities.ChannelsList

interface NewsApi {

    @Headers("Content-Type: application/json")
    @GET("/v2/sources/")
    fun getAllChannels(@Query("apiKey") apiKey: String): Observable<ChannelsList>

    @Headers("Content-Type: application/json")
    @GET("/v2/top-headlines/")
    fun getNewsForChannels(@Query("apiKey") apiKey: String, @Query("sources") sources: String): Observable<Articles>

    @Headers("Content-Type: application/json")
    @GET("/v2/top-headlines/")
    fun getTopHeadlinesByCountry(@Query("apiKey") apiKey: String, @Query("country") country: String): Observable<Articles>

    @Headers("Content-Type: application/json")
    @GET("/v2/top-headlines/")
    fun getTopHeadlinesByQuery(@Query("apiKey") apiKey: String, @Query("q") query: String) : Observable<Articles>

    @Headers("Content-Type: application/json")
    @GET("/v2/everything/")
    fun getevErythingByLanguage(@Query("apiKey") apiKey: String, @Query("sources") sources: String = "associated-press, google-news-ru, abc-new"): Observable<Articles>

    @Headers("Content-Type: application/json")
    @GET("/v2/everything/")
    fun getEverythingByQuery(@Query("apiKey") apiKey: String, @Query("q") query: String) : Observable<Articles>

}