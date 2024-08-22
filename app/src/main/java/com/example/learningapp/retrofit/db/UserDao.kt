package com.example.learningapp.retrofit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.learningapp.retrofit.models.Result


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(users : List<Result>)

    @Query("select * from users")
     fun  getUsers () : LiveData<List<Result>>

    @Query("select * from users")
    suspend  fun  getAllUsers () : List<Result>

     @Query("Delete from users")
    suspend fun deleteAllData()


}
