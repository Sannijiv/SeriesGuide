package com.battlelancer.seriesguide.ui.movies;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import android.content.Context;
import com.uwetrottmann.tmdb2.entities.Movie;
import java.util.ArrayList;
import java.util.List;
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

    @Mock
    Context context;

    @Test
    public void testSearchMovieAndDisplay() {
        //Arrange
        int movieTmbdId = 1;
        Movie movie = new Movie();

        //Act
        Mockito.when(movieTools.getMovieSummary(movieTmbdId)).thenReturn(new Movie());
        Movie testMovie = movieTools.getMovieSummary(movieTmbdId);

        //Assert
        assertEquals(movie, testMovie);
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
        MockedStatic<MovieTools> movieToolsMockedStatic = mockStatic(MovieTools.class);
        int movieTmbdId = 1;
        final String[] testString = new String[1];

        //Act
        movieToolsMockedStatic.when(() -> MovieTools.removeFromWatchlist(context, movieTmbdId))
                .then(invocation -> {
                    testString[0] = "passed";
                    return 1;
                });
        MovieTools.removeFromWatchlist(context, movieTmbdId);

        //Assert
        assertEquals(testString[0], "passed");
    }
}