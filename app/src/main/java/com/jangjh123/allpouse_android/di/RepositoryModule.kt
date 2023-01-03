package com.jangjh123.allpouse_android.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.repository.login.LoginRepository
import com.jangjh123.allpouse_android.data.repository.main.MainRepository
import com.jangjh123.allpouse_android.data.repository.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideSplashRepository(
        dataStore: DataStore<Preferences>
    ) = SplashRepository(dataStore)

    @ViewModelScoped
    @Provides
    fun provideLoginRepository(
        networkHelper: NetworkHelper,
        dataStore: DataStore<Preferences>
    ) = LoginRepository(networkHelper, dataStore)

    @ViewModelScoped
    @Provides
    fun provideMainRepository(
        networkHelper: NetworkHelper
    ) = MainRepository(networkHelper)
}