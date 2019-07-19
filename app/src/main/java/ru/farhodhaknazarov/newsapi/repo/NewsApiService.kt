package ru.farhodhaknazarov.newsapi.repo

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.farhodhaknazarov.newsapi.Constants
import ru.farhodhaknazarov.newsapi.repo.entities.Articles
import ru.farhodhaknazarov.newsapi.repo.entities.ChannelsList
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class NewsApiService {

    companion object {
        @JvmField
        var instance: NewsApiService

        @JvmField
        var service: NewsApi

        init {
            service = getApiService()
            instance = NewsApiService()
        }

        fun getApiService(): NewsApi {
            var gson: Gson = GsonBuilder().setLenient().create()
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getUnsafeOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val request = retrofit.create<NewsApi>(NewsApi::class.java)
            return request

        }

        fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                })

                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(
                    null, trustAllCerts,
                    java.security.SecureRandom()
                )
                val sslSocketFactory = sslContext
                    .socketFactory

                var okHttpClient = OkHttpClient()
                okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build()

                return okHttpClient
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    }


    fun <T> getChannels(): Observable<ChannelsList>? {
        var observable = service
            .getAllChannels(Constants.apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }

    fun <T> getNewsForChannels(sources: String): Observable<Articles>?{
        var observable = service
            .getNewsForChannels(Constants.apiKey, sources)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }

    fun <T> getArticlesForRu(): Observable<Articles>? {
        var observable = service
            .getTopHeadlinesByCountry(Constants.apiKey, "ru")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }

    fun <T> getArticlesByQuery(query: String): Observable<Articles>? {
        var observable = service
            .getTopHeadlinesByQuery(Constants.apiKey, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }

    fun <T> getArticlesByLanguage(): Observable<Articles>? {
        var observable = service
            .getevErythingByLanguage(Constants.apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }

    fun <T> getArticlesByQueryEveruthing(query: String): Observable<Articles>? {
        var observable = service
            .getEverythingByQuery(Constants.apiKey, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable
    }
}