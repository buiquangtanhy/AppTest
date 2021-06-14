package com.example.appTest.di

import javax.inject.Qualifier

// -------------------------------------- APP CONFIG -----------------------------------------------

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharePreferenceFileName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JSonPrettyPrint

// ------------------------------------ RETROFIT CONFIG --------------------------------------------

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseURL

// ------------------------------------ REFRESH TIME CONFIG ----------------------------------------

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FetchLimitTime

// ------------------------------------ COROUTINE CONFIG -------------------------------------------

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CPUDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher
