package org.d3if1135.ngampusrek.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.d3if1135.ngampusrek.db.NgampusEntity

@Dao
interface NgampusDao {
    @Insert
    fun insert(utbk: NgampusEntity)
    @Query("SELECT * FROM ngampus ORDER BY id DESC")
    fun getLastUtbk(): LiveData<List<NgampusEntity?>>
    @Query("DELETE FROM ngampus")
    fun clearData()

}