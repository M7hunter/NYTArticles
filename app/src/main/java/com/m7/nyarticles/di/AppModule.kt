package com.m7.nyarticles.di

import com.m7.nyarticles.BuildConfig
import com.m7.nyarticles.data.source.ArticlesDataSource
import com.m7.nyarticles.data.source.remote.APIs
import com.m7.nyarticles.data.source.remote.ArticlesRemoteDataSource
import com.m7.nyarticles.data.source.repo.DefaultArticlesRepo
import com.m7.nyarticles.data.source.repo.ArticlesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideArticlesRepo(articlesDataSource: ArticlesDataSource) =
        DefaultArticlesRepo(articlesDataSource) as ArticlesRepo

    @Provides
    fun provideArticlesRemoteDateSource(apIs: APIs) =
        ArticlesRemoteDataSource(apIs) as ArticlesDataSource

    @Provides
    fun provideAPIs(okHttpClient: OkHttpClient): APIs = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIs::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
        }.build()
}