package org.d3if1135.ngampusrek.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NgampusEntity::class], version = 2, exportSchema = false)
abstract class NgampusDb : RoomDatabase(){
    abstract val dao: NgampusDao
    companion object {
        @Volatile
        private var INSTANCE: NgampusDb? = null
        fun getInstance(context: Context): NgampusDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NgampusDb::class.java,
                        "ngampus.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
