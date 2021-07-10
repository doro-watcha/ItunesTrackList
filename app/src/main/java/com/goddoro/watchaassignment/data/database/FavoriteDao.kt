package com.goddoro.watchaassignment.data.database

import androidx.room.*
import io.reactivex.Completable

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: FavoriteItem)

    @Delete
    fun delete(vararg item: FavoriteItem?)

    @Query("SELECT * FROM FavoriteItem ORDER BY `index` ")
    fun list(): List<FavoriteItem>

}