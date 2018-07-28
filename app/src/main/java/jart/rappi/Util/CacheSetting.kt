package jart.rappi.Util
import android.content.Context
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by julianx1 on 7/27/18.
 */

class CacheSetting {
    companion object Factory {


        fun getSetting(context:Context): OkHttpClient {

            val cacheSize =Api.cache_size
            val httpCacheDirectory = File(context.cacheDir, "http-cache")
            val cache = okhttp3.Cache(httpCacheDirectory, cacheSize.toLong())

            // create a network cache interceptor, setting the max age to 10 minute
            val networkCacheInterceptor = Interceptor { chain ->
                val response = chain.proceed(chain.request())

                var cacheControl = CacheControl.Builder()
                        .maxAge(Api.time_cache_min, TimeUnit.MINUTES)
                        .build()

                response.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build()
            }

            // Create the logging interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


            // Create the httpClient, configure it
            // with cache, network cache interceptor and logging interceptor
            val httpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .addNetworkInterceptor(networkCacheInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()

            return httpClient

        }
    }
}