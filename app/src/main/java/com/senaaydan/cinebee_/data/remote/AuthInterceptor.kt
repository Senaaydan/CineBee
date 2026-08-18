package com.senaaydan.cinebee_.data.remote



import okhttp3.Interceptor
import okhttp3.Response
import com.senaaydan.cinebee_.BuildConfig
import javax.inject.Inject


class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.TMDB_TOKEN}"
            )
            .build()

        return chain.proceed(request)
    }
}