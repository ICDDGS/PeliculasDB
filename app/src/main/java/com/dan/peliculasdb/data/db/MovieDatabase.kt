package com.dan.peliculasdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.util.Constants

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase: RoomDatabase()  {

    abstract fun movieDao(): MovieDAO

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase{
            return INSTANCE?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()
                INSTANCE = instance
                return instance
            }
        }


    }

}