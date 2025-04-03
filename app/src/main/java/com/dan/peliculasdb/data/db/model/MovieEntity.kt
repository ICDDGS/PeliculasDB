package com.dan.peliculasdb.data.db.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dan.peliculasdb.util.Constants

@Entity(tableName = Constants.DATABASE_MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    var id: Long = 0,
    @ColumnInfo(name = "movie_title")
    var title: String = "",
    @ColumnInfo(name = "movie_genre")
    var genre: String = "",
    @ColumnInfo(name = "movie_company")
    var company: String = "",
    @ColumnInfo(name = "movie_director")
    var director: String = "",
    @ColumnInfo(name = "movie_year")
    var year: String = "",
    @ColumnInfo(name = "movie_image")
    var image: String = "",
)


