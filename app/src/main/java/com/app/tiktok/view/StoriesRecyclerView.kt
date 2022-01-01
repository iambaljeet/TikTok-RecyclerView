package com.app.tiktok.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tiktok.model.StoriesDataModel
import com.app.tiktok.ui.home.adapter.StoriesViewHolder
import com.app.tiktok.utils.MediaManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class StoriesRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs), KoinComponent {
    private val TAG = "ViewSupportedRecycler"

    private val mediaManager: MediaManager by inject()

    private var scrollingUp = false
    private var toPlayPosition = -1
    private var currentPlayingPosition = -1
    private var dataList = arrayListOf<StoriesDataModel>()

    private val player: ExoPlayer = ExoPlayer.Builder( /* context= */context)
        .setMediaSourceFactory(mediaManager.mediaSourceFactory)
        .build()

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollingUp = dy > 0

                if (dy == 0) {
                    playVideo()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    playVideo()
                } else {
                    player.pause()
                }
            }
        })
    }

    private fun playVideo() {
        val firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        val lastCompletelyVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        val firstCompletelyVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

        val position = if (!scrollingUp) {
                lastVisibleItemPosition
        } else {
                firstVisibleItemPosition
        }

        if (position >= 0) {
            toPlayPosition = position
        }

        if (toPlayPosition != currentPlayingPosition) {

            currentPlayingPosition = if (!scrollingUp) {
                lastCompletelyVisibleItemPosition
            } else {
                firstCompletelyVisibleItemPosition
            }

            if (currentPlayingPosition < 0) {
                currentPlayingPosition = toPlayPosition
            }

            player.stop()

            val currentViewHolder = findViewHolderForAdapterPosition(currentPlayingPosition)
            val currentViewBinding = (currentViewHolder as? StoriesViewHolder)?.viewBinding
            val currentPlayerViewStory = currentViewBinding?.layoutStoryView?.playerViewStory
            currentPlayerViewStory?.player = null

            val viewHolder = findViewHolderForAdapterPosition(toPlayPosition)
            val viewBinding = (viewHolder as? StoriesViewHolder)?.viewBinding
            val playerViewStory = viewBinding?.layoutStoryView?.playerViewStory
            playerViewStory?.player = player

            val storiesDataModel = dataList[currentPlayingPosition]

            player.addListener(object: Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    Log.d(TAG, "onPlaybackStateChanged: playbackState: $playbackState")
                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)

                    Log.d(TAG, "onPlayerError: error: $error")
                }

                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)

                    Log.d(TAG, "onEvents: events: $events")
                }
            })

            val mediaItem: MediaItem = MediaItem.Builder()
                .setMediaId(storiesDataModel.storyId.toString())
                .setUri(storiesDataModel.storyUrl.toUri())
                .build()

            player.playWhenReady = true
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } else {
            player.play()
        }
    }

    fun submitList(list: ArrayList<StoriesDataModel>) {
        val shouldPlayFirstVideo = dataList.isEmpty() && list.isNotEmpty()
        dataList = list
        (adapter as? ListAdapter<StoriesDataModel, ViewHolder>)?.submitList(list)

        if (shouldPlayFirstVideo) {
            toPlayPosition = 0
            scrollToPosition(0)
        }
    }

    fun onDestroy() {
        player.stop()
        player.release()
    }
}