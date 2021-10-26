package com.esmaeel.composestructure.di

import com.esmaeel.composestructure.BuildConfig
import com.esmaeel.composestructure.data.MoviesService
import com.esmaeel.composestructure.data.TOKEN_REQUIRED
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideUsersService(retrofit: Retrofit.Builder): MoviesService = retrofit
        .baseUrl("https://api.themoviedb.org/3/")
        .build()
        .create(MoviesService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )


    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
        keysInjectionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(keysInjectionInterceptor)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideKeysInjectionInterceptor(/*maybe provide shared preference here*/): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val method = chain.request().tag(Invocation::class.java)!!.method()
            val builder = original.newBuilder()
            when {
                method.isAnnotationPresent(TOKEN_REQUIRED::class.java) -> { }
            }
            chain.proceed(builder.method(original.method, original.body).build())
        }
    }
}