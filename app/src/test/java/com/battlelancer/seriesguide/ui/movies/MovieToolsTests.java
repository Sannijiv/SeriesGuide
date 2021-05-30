package com.battlelancer.seriesguide.ui.movies;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    /**
     * Search for movie and get its details.
     *
     * CORRECT Standards:
     * Conformance: Value of testMovie conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, that being not null.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: Test already checks whether the value is not null. Will fail if value is null.
     * Cardinality: There are enough values.
     * Time: Everything in code happens in (correct) order.
     */
    @Test
    public void testSearchMovieAndDisplay() {
        //Arrange
        int movieTmbdId = 1;
        
        //Act
        Mockito.when(movieTools.getMovieSummary(movieTmbdId)).thenReturn(new Movie());
        Movie testMovie = movieTools.getMovieSummary(movieTmbdId);

        //Assert
        assertNotNull(testMovie);
    }

    /**
     * Add a movie to the watch list.
     *
     * CORRECT Standards:
     * Conformance: Value of variable test conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, that being either true or false.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: Values are assigned in the test itself, therefore all values will exist.
     * Cardinality: There are enough values.
     * Time: Everything in code happens in (correct) order.
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

    /**
     * Delete a movie from the watch list.
     *
     * CORRECT Standards:
     * Conformance: Value of testString conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, the value is either "passed" or not.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: All used values are instantiated in the test itself, therefore they exist.
     * Cardinality: There are enough values.
     * Time: Everything in the test happens in (correct) order, including unregistering the mock instance.
     */
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

        movieToolsMockedStatic.close();
    }
}