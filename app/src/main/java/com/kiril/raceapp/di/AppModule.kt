package com.kiril.raceapp.di

import android.annotation.SuppressLint
import com.kiril.raceapp.BuildConfig
import com.kiril.raceapp.data.driver.repository.DriverRepository
import com.kiril.raceapp.data.network.ErgastApi
import com.kiril.raceapp.data.race.repository.RaceRepository
import com.kiril.raceapp.data.race_detail.repository.RaceDetailsRepository
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(JsonInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideErgastApi(retrofit: Retrofit): ErgastApi {
        return retrofit.create(ErgastApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRaceRepository(ergastApi: ErgastApi): RaceRepository {
        return RaceRepository(ergastApi)
    }

    @Provides
    @Singleton
    fun provideRaceDetailsRepository(ergastApi: ErgastApi): RaceDetailsRepository {
        return RaceDetailsRepository(ergastApi)
    }

    @Provides
    @Singleton
    fun provideDriverDetailsRepository(ergastApi: ErgastApi): DriverRepository {
        return DriverRepository(ergastApi)
    }

    private fun createUnsafeClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            okHttpClient
        } catch (e: Exception) {
            okHttpClient
        }
    }
}