package com.example.syriamarket.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PubgInterface {
        @GET("{file}.php?action=getPlayerName")
        fun getPubgPlayerName (@Path("file") file: String, @Query("game") game: String, @Query("playerID") playerID: String) :Call<String>

}