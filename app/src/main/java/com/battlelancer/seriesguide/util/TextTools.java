package com.battlelancer.seriesguide.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.battlelancer.seriesguide.R;
import com.battlelancer.seriesguide.settings.DisplaySettings;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * Tools to help build text fragments to be used throughout the user interface.
 */
public class TextTools {

    private TextTools() {
        // prevent instantiation
    }

    /**
     * Returns the episode number formatted according to the users preference (e.g. '1x1',
     * 'S1:E1', ...). If {@code episode} is -1 only the season number is returned.
     */
    public static String getEpisodeNumber(Context context, int season, int episode) {
        String format = DisplaySettings.getNumberFormat(context);
        boolean useLongNumbers = format.endsWith("-long");

        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);

        String seasonStr;
        if (season < 10 && useLongNumbers) {
            // Make season number at least two chars long.
            seasonStr = numberFormat.format(0) + numberFormat.format(season);
        } else {
            seasonStr = numberFormat.format(season);
        }

        boolean includeEpisode = episode != -1;
        String episodeStr = null;
        if (includeEpisode) {
            if (episode < 10
                    && (useLongNumbers || DisplaySettings.NUMBERFORMAT_DEFAULT.equals(format))) {
                // Make episode number at least two chars long.
                episodeStr = numberFormat.format(0) + numberFormat.format(episode);
            } else {
                episodeStr = numberFormat.format(episode);
            }
        }

        if (format.startsWith("englishlower")) {
            // s1:e1 or s01e01 format
            if (includeEpisode) {
                return "s" + seasonStr + (useLongNumbers ? "" : ":") + "e" + episodeStr;
            } else {
                return "s" + seasonStr;
            }
        } else if (format.startsWith("english")) {
            // S1:E1 or S01E01 format
            if (includeEpisode) {
                return "S" + seasonStr + (useLongNumbers ? "" : ":") + "E" + episodeStr;
            } else {
                return "S" + seasonStr;
            }
        } else {
            // Default
            // 1x01 format
            if (includeEpisode) {
                return seasonStr + "x" + episodeStr;
            } else {
                return seasonStr + "x";
            }
        }
    }

    /**
     * Returns the title or if its empty a string like "Episode 2".
     */
    @NonNull
    public static String getEpisodeTitle(Context context, String title, int episode) {
        return TextUtils.isEmpty(title)
                ? context.getString(R.string.episode_number, episode)
                : title;
    }

    /**
     * Returns a string like "1x01 Title". The number format may change based on user preference.
     */
    public static String getNextEpisodeString(Context context, int season, int episode,
            String title) {
        return getEpisodeNumber(context, season, episode) + " "
                + getEpisodeTitle(context, title, episode);
    }

    /**
     * Returns a string like "Title 1x01". The number format may change based on user preference.
     */
    public static String getShowWithEpisodeNumber(Context context, String title, int season,
            int episode) {
        String number = getEpisodeNumber(context, season, episode);
        title += " " + number;
        return title;
    }

    /**
     * Splits the string on the pipe character {@code "|"} and reassembles it, separating the items
     * with commas. The given object is returned with the new string.
     *
     * @see #mendTvdbStrings(List)
     */
    @NonNull
    public static String splitAndKitTVDBStrings(@Nullable String tvdbstring) {
        if (tvdbstring == null) {
            return "";
        }
        String[] split = tvdbstring.split("\\|");
        StringBuilder builder = new StringBuilder();
        for (String item : split) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(item.trim());
        }
        return builder.toString();
    }

    /**
     * Combines the strings into a single string, separated by the pipe character {@code "|"}.
     *
     * @see #splitAndKitTVDBStrings(String)
     */
    @NonNull
    public static String mendTvdbStrings(@Nullable List<String> strings) {
        if (strings == null || strings.size() == 0) {
            return "";
        }
        // pre-size builder based on reasonable average length of a string
        StringBuilder result = new StringBuilder(strings.size() * 10);
        for (String string : strings) {
            if (result.length() > 0) {
                result.append("|");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * Dot separates the two given strings. If one is empty, just returns the other string (no dot).
     */
    @NonNull
    public static String dotSeparate(@Nullable String left, @Nullable String right) {
        StringBuilder dotString = new StringBuilder(left == null ? "" : left);
        if (!TextUtils.isEmpty(right)) {
            if (dotString.length() > 0) {
                dotString.append(" · ");
            }
            dotString.append(right);
        }
        return dotString.toString();
    }

    /**
     * Builds a network + release time string for a show formatted like "Network · Tue 08:00 PM".
     */
    @NonNull
    public static String networkAndTime(Context context, @Nullable Date release, int weekDay,
            @Nullable String network) {
        if (release != null) {
            String dayString = TimeTools.formatToLocalDayOrDaily(context, release, weekDay);
            String timeString = TimeTools.formatToLocalTime(context, release);
            return TextTools.dotSeparate(network, dayString + " " + timeString);
        } else {
            return TextTools.dotSeparate(network, null);
        }
    }

    /**
     * Appends an empty new line and a new line listing the source of the text as TMDB.
     */
    public static SpannableStringBuilder textWithTmdbSource(Context context, @Nullable String text) {
        return textWithSource(context, text,
                context.getString(R.string.format_source, context.getString(R.string.tmdb)));
    }

    private static SpannableStringBuilder textWithSource(Context context, @Nullable String text,
            @NonNull String source) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (text != null) {
            builder.append(text);
            builder.append("\n\n");
        }
        int sourceStartIndex = builder.length();
        builder.append(source);
        builder.setSpan(new TextAppearanceSpan(context, R.style.TextAppearance_SeriesGuide_Body2_Italic),
                sourceStartIndex, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return builder;
    }
}
