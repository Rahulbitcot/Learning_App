package com.example.learningapp.retrofit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.learningapp.retrofit.models.Result


@Database (entities = [Result::class] , version = 1)
@TypeConverters (Convertor::class)
abstract  class UserDatabase :RoomDatabase(){
     abstract  fun userDao() : UserDao


     companion object{
         private var Instances : UserDatabase? = null

         fun getDatabase(context :Context) : UserDatabase? {
             if(Instances == null){
                 synchronized(this){
                     Instances = Room.databaseBuilder(context,
                         UserDatabase::class.java,
                         "user")
                         .build()
                 }
             }
             return Instances
         }
     }
}