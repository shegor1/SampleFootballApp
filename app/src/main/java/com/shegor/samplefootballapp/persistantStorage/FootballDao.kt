package com.shegor.samplefootballapp.persistantStorage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shegor.samplefootballapp.models.League

@Dao
interface FootballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeague(league: League)

    @Query("SELECT * from league_table")
    fun getAllLeagues(): LiveData<List<League>>
}