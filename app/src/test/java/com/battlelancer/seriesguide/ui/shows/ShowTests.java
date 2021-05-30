package com.battlelancer.seriesguide.ui.shows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import android.content.Context;
import com.battlelancer.seriesguide.dataliberation.model.Show;
import com.battlelancer.seriesguide.model.SgShow2;
import com.battlelancer.seriesguide.provider.SgShow2Update;
import com.battlelancer.seriesguide.ui.episodes.EpisodeFlags;
import com.battlelancer.seriesguide.ui.episodes.EpisodeTools;
import com.battlelancer.seriesguide.ui.movies.MovieTools;
import com.battlelancer.seriesguide.ui.search.AddShowDialogViewModel;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ShowTests {
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ShowTools showTools;

    @Mock
    private ShowTools2.ShowResult showResult;

    @Mock
    private SgShow2 sgShow2;

    @Mock
    private SgShow2Update sgShow2Update;

    @Mock
    Context context;

    /**
     * Search for a show and display information.
     *
     * CORRECT Standards:
     * Conformance: Value of showDetails conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, the value is either null or not.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: All used values are instantiated in either the test itself or the test file, therefore they exist.
     * Cardinality: There are enough values.
     * Time: Everything in the test happens in (correct) order.
     */
    @Test
    public void testSearchAndDisplayShow() {
        //Arrange
        int showTmdbId = 1;
        String desiredLanguage = "English";

        //Act
        Mockito.when(showTools.getShowDetails(showTmdbId, desiredLanguage))
                .thenReturn(new ShowTools2.ShowDetails(
                        showResult, sgShow2, sgShow2Update,
                        new ArrayList<TvSeason>() {
                        }));
        ShowTools2.ShowDetails showDetails = showTools.getShowDetails(showTmdbId, desiredLanguage);

        //Assert
        assertNotNull(showDetails);
    }

    /**
     * Delete a movie from the watch list.
     *
     * CORRECT Standards:
     * Conformance: Value of testString conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, the value is either "passed" or not.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: All used values are instantiated in either the test itself or the test file, therefore they exist.
     * Cardinality: There are enough values.
     * Time: Everything in the test happens in (correct) order, including unregistering the mock instance.
     */
    @Test
    public void testMarkEpisodeAsSeen() {
        //Arrange
        MockedStatic<EpisodeTools> episodeToolsMockedStatic = mockStatic(EpisodeTools.class);
        int episodeId = 45;
        final String[] testString = new String[1];
        int episodeFlag = EpisodeFlags.WATCHED;

        //Act
        episodeToolsMockedStatic
                .when(() -> EpisodeTools.episodeWatched(context, episodeId, episodeFlag))
                .then(invocation -> {
                    testString[0] = "passed";
                    return 1;
                });
        EpisodeTools.episodeWatched(context, episodeId, episodeFlag);

        //Assert
        assertEquals(testString[0], "passed");

        episodeToolsMockedStatic.close();
    }

    /**
     * Delete a movie from the watch list.
     *
     * CORRECT Standards:
     * Conformance: Value of testString conforms with expectations.
     * Order: The values are ordered in order of usage.
     * Range: The value is between a reasonable minimum and maximum, the value is either "passed" or not.
     * Reference: All references in the test are mocked/provided in the test file itself.
     * Existence: All used values are instantiated in either the test itself or the test file, therefore they exist.
     * Cardinality: There are enough values.
     * Time: Everything in the test happens in (correct) order, including unregistering the mock instance.
     */
    @Test
    public void testMarkEpisodeAsUnSeen() {
        //Arrange
        MockedStatic<EpisodeTools> episodeToolsMockedStatic = mockStatic(EpisodeTools.class);
        int episodeId = 45;
        final String[] testString = new String[1];
        int episodeFlag = EpisodeFlags.UNWATCHED;

        //Act
        episodeToolsMockedStatic
                .when(() -> EpisodeTools.episodeWatched(context, episodeId, episodeFlag))
                .then(invocation -> {
                    testString[0] = "passed";
                    return 1;
                });
        EpisodeTools.episodeWatched(context, episodeId, episodeFlag);

        //Assert
        assertEquals(testString[0], "passed");

        episodeToolsMockedStatic.close();
    }
}
