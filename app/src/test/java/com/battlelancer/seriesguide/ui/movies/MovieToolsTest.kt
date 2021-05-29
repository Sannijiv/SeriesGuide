package com.battlelancer.seriesguide.ui.movies

import android.content.Context
import androidx.test.core.app.ApplicationProvider

class MovieToolsTest {


    fun testAddToWatchlist() {
        //Arrange
        val context = ApplicationProvider.getApplicationContext<Context>()
        val movieTmbdId = 42

        //Act
        MovieTools.addToWatchlist(context, movieTmbdId)



        //Assert


    }


}