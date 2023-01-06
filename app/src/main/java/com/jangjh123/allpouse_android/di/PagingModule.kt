package com.jangjh123.allpouse_android.di

import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.paging.DataPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object PagingModule {

    @ViewModelScoped
    @Provides
    fun providePagingSource(
        networkHelper: NetworkHelper,
    ) = DataPagingSource(networkHelper)
}