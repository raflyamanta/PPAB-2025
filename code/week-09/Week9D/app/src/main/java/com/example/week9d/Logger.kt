package com.example.week9d

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggerServiceImpl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggerServiceImpl2

interface LoggerService {
    fun LoggerMethod()
}

class MyLogger @Inject constructor(
    private val loggerService: LoggerService
){
    fun log(){
        loggerService.LoggerMethod()
    }
}

class LoggerServiceImplA @Inject constructor() : LoggerService {
    override fun LoggerMethod() {
        Log.d("Logger_bind", "Motto")
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class LoggerModule1 {

    @Binds
    abstract fun bindLoggerService(
        impl: LoggerServiceImplA
    ): LoggerService
}

class LoggerServiceImpl private constructor(val micin: String) : LoggerService{
    override fun LoggerMethod() {
        Log.d("Logger", micin)
    }

    class Builder {
        private var micin: String = "Royco"

        fun addMicin(micinName: String) = apply { this.micin = micinName }

        fun build(): LoggerServiceImpl {
            return LoggerServiceImpl(micin)
        }
    }
}

@Module
@InstallIn(ActivityComponent::class)
object LoggerModule2 {

    @LoggerServiceImpl1
    @Provides
    fun LoggerService1(
    ): LoggerService {
        return LoggerServiceImpl.Builder().addMicin("Ajinomoto").build()
    }

    @LoggerServiceImpl2
    @Provides
    fun LoggerService2(
    ): LoggerService {
        return LoggerServiceImpl.Builder().addMicin("Masako").build()
    }
}