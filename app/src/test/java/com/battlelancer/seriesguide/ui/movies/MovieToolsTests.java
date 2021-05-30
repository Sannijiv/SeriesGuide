package com.battlelancer.seriesguide.ui.movies;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import com.uwetrottmann.tmdb2.entities.Movie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MovieToolsTests {

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private MovieTools movieTools;
    MockedStatic<MovieTools> theMock = Mockito.mockStatic(MovieTools.class);

    @Mock
    Context context;

    @Test
    public void testSearchMovieAndDisplay() {
        //Arrange
        int movieTmbdId = 1;

        //Act
        Mockito.when(movieTools.getMovieSummary(movieTmbdId)).thenReturn(new Movie());

        //Assert
    }

    /*


     */
    @Test
    public void testAddMovieToWatchlist() {
        //Arrange
        int movieTmbdId = 1;
        MovieTools.Lists list = MovieTools.Lists.WATCHLIST;

        //Act
        Mockito.when(movieTools.addToList(movieTmbdId, list))
                .thenReturn(true);
        boolean test = movieTools.addToList(movieTmbdId, list);

        //Assert
        assertTrue(test);
    }

    @Test
    public void testDeleteMovieFromWatchlist() {
        //Arrange
        int movieTmbdId = 1;

        theMock.when(() -> MovieTools.removeFromWatchlist(context, movieTmbdId));

        //Assert

    }
}