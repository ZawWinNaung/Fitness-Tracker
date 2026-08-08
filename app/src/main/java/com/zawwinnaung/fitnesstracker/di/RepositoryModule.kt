package com.zawwinnaung.fitnesstracker.di

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.data.repository.ApiRepositoryImpl
import com.zawwinnaung.fitnesstracker.data.repository.DbRepository
import com.zawwinnaung.fitnesstracker.data.repository.DbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl
    ): ApiRepository

    @Binds
    @Singleton
    abstract fun bindDbRepository(
        dbRepositoryImpl: DbRepositoryImpl
    ): DbRepository
}