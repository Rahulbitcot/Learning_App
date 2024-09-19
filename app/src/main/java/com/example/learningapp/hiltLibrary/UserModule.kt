package com.example.learningapp.hiltLibrary

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @Provides
    @Named("print1")
     fun providesPrint1() : MyRepository{
         return Print1()
     }

    @Provides
    @Named("print2")
    fun providesPrint2() : MyRepository{
        return Print2()
    }
}