package com.sampleweather.di.module

import android.content.Context
import androidx.room.Room
import com.sampleweather.data.db.AppDatabase

import com.sampleweather.data.network.ApiService
import com.sampleweather.data.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getBaseUrl(): String = "http://api.openweathermap.org/data/2.5/"


    @Provides
    @Singleton
    fun getInterceptor(@ApplicationContext app: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkConnectionInterceptor(app))
        .build()


    @Provides
    @Singleton
    fun getRetrofitBuilder(@ApplicationContext app: Context,baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getInterceptor(app))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "your_db_name"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.locationDAO()

}