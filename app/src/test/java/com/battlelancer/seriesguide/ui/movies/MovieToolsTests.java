package com.battlelancer.seriesguide.ui.movies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.uwetrottmann.seriesguide.backend.movies.model.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.trakt5.services.Search;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MovieToolsTests {

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private MovieTools movieTools;

    @Test
    public void testSearchMovieAndDisplay() {
        //Arrange
        int movieTmbdId = 42;
        MovieTools.Lists list = MovieTools.Lists.WATCHLIST;

        //Act
        Mockito.when(movieTools.getMovieSummary(movieTmbdId)).thenReturn(Movie);
        boolean test = movieTools.addToList(movieTmbdId, list);

        //Assert
        assertTrue(test);
    }

    @Test
    public void testAddMovieToWatchlist() {
        //Arrange
        int movieTmbdId = 42;
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
        int movieTmbdId = 42;
        MovieTools.Lists list = MovieTools.Lists.WATCHLIST;

        //Act
        Mockito.when(movieTools.addToList(movieTmbdId, list))
                .thenReturn(true);
        boolean test = movieTools.addToList(movieTmbdId, list);

        //Assert
        assertTrue(test);
    }


}
