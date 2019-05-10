package com.battlelancer.seriesguide.ui.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.battlelancer.seriesguide.R
import com.battlelancer.seriesguide.settings.DisplaySettings
import com.battlelancer.seriesguide.thetvdbapi.TvdbImageTools
import com.battlelancer.seriesguide.ui.episodes.EpisodeTools
import com.battlelancer.seriesguide.util.TextTools
import com.battlelancer.seriesguide.util.TimeTools
import com.battlelancer.seriesguide.widgets.WatchedBox
import com.uwetrottmann.androidutils.CheatSheet

class CalendarItemViewHolder(
    itemView: View,
    itemClickListener: CalendarAdapter2.ItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val showTextView: TextView = itemView.findViewById(R.id.textViewActivityShow)
    private val episodeTextView: TextView = itemView.findViewById(R.id.textViewActivityEpisode)
    private val collected: View = itemView.findViewById(R.id.imageViewActivityCollected)
    private val watchedBox: WatchedBox = itemView.findViewById(R.id.watchedBoxActivity)
    private val info: TextView = itemView.findViewById(R.id.textViewActivityInfo)
    private val timestamp: TextView = itemView.findViewById(R.id.textViewActivityTimestamp)
    private val poster: ImageView = itemView.findViewById(R.id.imageViewActivityPoster)

    private var item: CalendarFragment2ViewModel.CalendarItem? = null

    init {
        itemView.setOnClickListener {
            item?.episode?.let {
                itemClickListener.onItemClick(it.episodeTvdbId)
            }
        }
        itemView.setOnLongClickListener {
            item?.episode?.let {
                itemClickListener.onItemLongClick(itemView, it)
            }
            true
        }
        watchedBox.setOnClickListener {
            item?.episode?.let {
                itemClickListener.onItemWatchBoxClick(
                    it,
                    EpisodeTools.isWatched(watchedBox.episodeFlag)
                )
            }
        }

        CheatSheet.setup(watchedBox)
    }

    fun bind(
        context: Context,
        item: CalendarFragment2ViewModel.CalendarItem
    ) {
        this.item = item
        val episode = item.episode!!

        // show title
        showTextView.text = episode.seriestitle

        // episode number and title
        val hideTitle =
            EpisodeTools.isUnwatched(episode.watched) && DisplaySettings.preventSpoilers(context)
        episodeTextView.text = TextTools.getNextEpisodeString(
            context,
            episode.season,
            episode.episodenumber,
            if (hideTitle) null else episode.episodetitle
        )

        // timestamp, absolute time and network
        val releaseTime = episode.episode_firstairedms
        val time = if (releaseTime != -1L) {
            val actualRelease = TimeTools.applyUserOffset(context, releaseTime)
            // timestamp
            timestamp.text = if (DisplaySettings.isDisplayExactDate(context)) {
                TimeTools.formatToLocalDateShort(context, actualRelease)
            } else {
                TimeTools.formatToLocalRelativeTime(context, actualRelease)
            }
            // release time of this episode
            TimeTools.formatToLocalTime(context, actualRelease)
        } else {
            timestamp.text = null
            null
        }
        info.text = TextTools.dotSeparate(episode.network, time)

        // watched box
        val episodeFlag = episode.watched
        watchedBox.episodeFlag = episodeFlag
        val watched = EpisodeTools.isWatched(episodeFlag)
        watchedBox.contentDescription =
            context.getString(if (watched) R.string.action_unwatched else R.string.action_watched)

        // collected indicator
        collected.isGone = !episode.episode_collected

        // set poster
        TvdbImageTools.loadShowPosterResizeSmallCrop(
            context, poster,
            TvdbImageTools.smallSizeUrl(episode.poster)
        )
    }

    companion object {

        fun create(
            parent: ViewGroup,
            itemClickListener: CalendarAdapter2.ItemClickListener
        ): CalendarItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
            return CalendarItemViewHolder(view, itemClickListener)
        }

    }

}