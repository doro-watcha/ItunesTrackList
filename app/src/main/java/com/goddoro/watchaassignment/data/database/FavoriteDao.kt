package com.goddoro.watchaassignment.data.database

import androidx.room.*
import io.reactivex.Completable

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteItem)

    @Delete
    suspend fun delete(vararg item: FavoriteItem?)

    @Query("SELECT * FROM `FavoriteItem` ORDER BY `index` ")
    suspend fun list(): List<FavoriteItem>

}