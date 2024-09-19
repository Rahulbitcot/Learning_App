package com.example.learningapp.hiltLibrary

import javax.inject.Inject


interface MyRepository {
    fun getWelcomeMessage():String
}

class Print1 @Inject constructor() : MyRepository {
    override fun getWelcomeMessage() = "Print 1"

}

class Print2 @Inject constructor() : MyRepository {
    override fun getWelcomeMessage() = "Print 2"
}
