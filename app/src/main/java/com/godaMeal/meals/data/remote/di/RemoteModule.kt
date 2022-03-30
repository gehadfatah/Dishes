package  com.godaMeal.meals.data.remote.di


import com.godaMeal.meals.BuildConfig
import com.godaMeal.meals.data.remote.interceptor.ErrorMappingInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://elmenus-assignment.getsandbox.com"

const val CONNECT_TIMEOUT: Long = 30
const val READ_TIMEOUT: Long = 30
const val WRITE_TIMEOUT: Long = 30
val remoteModule = module {

    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    single { ErrorMappingInterceptor(get(), get()) }
    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(get<ErrorMappingInterceptor>())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }

    single { GsonBuilder().serializeNulls().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }


}