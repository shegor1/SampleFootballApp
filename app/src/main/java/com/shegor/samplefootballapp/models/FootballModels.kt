package com.shegor.samplefootballapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "league_table")
@Parcelize
data class League(
    @PrimaryKey @Json(name = "league_id")
    val leagueId: Int,

    @Json(name = "league_name")
    val leagueName: String,

    @Json(name = "country_name")
    val countryName: String,

    @Json(name = "league_logo")
    val leagueLogoUrl: String
) : Parcelable

data class MatchModel(

    @Json(name = "match_id")
    val matchId: Int,

    @Json(name = "league_name")
    val leagueName: String,

    @Json(name = "league_logo")
    val leagueLogoUrl: String,

    @Json(name = "match_hometeam_name")
    val hometeamName: String,

    @Json(name = "match_hometeam_score")
    val hometeamScore: String,

    @Json(name = "match_awayteam_name")
    val awayteamName: String,

    @Json(name = "match_awayteam_score")
    val awayteamScore: String,

    @Json(name = "team_home_badge")
    val hometeamLogo: String,

    @Json(name = "team_away_badge")
    val awayteamLogo: String,

    @Json(name = "match_date")
    var matchDate: String
)