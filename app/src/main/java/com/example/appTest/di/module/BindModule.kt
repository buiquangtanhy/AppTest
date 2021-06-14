package com.example.appTest.di.module

import com.example.appTest.repository.Repo
import com.example.appTest.repository.RepoImp


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Singleton
    @Binds
    abstract fun bindRepo(impl: RepoImp): Repo


}