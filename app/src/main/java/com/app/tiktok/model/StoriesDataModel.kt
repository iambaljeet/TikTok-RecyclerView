package com.app.tiktok.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoriesDataModel(
    val storyId: Long,
    val storyUrl: String,
    val storyThumbUrl: String? = null,
    val storyDescription: String? = null,
    val musicCoverTitle: String,
    val musicCoverImageLink: String? = null,
    val userId: String,
    val userProfilePicUrl: String? = null,
    val userName: String,
    val likesCount: Long,
    val commentsCount: Long,
    val contentWarning: String? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoriesDataModel

        if (storyId != other.storyId) return false
        if (storyUrl != other.storyUrl) return false
        if (storyThumbUrl != other.storyThumbUrl) return false
        if (storyDescription != other.storyDescription) return false
        if (musicCoverTitle != other.musicCoverTitle) return false
        if (musicCoverImageLink != other.musicCoverImageLink) return false
        if (userId != other.userId) return false
        if (userProfilePicUrl != other.userProfilePicUrl) return false
        if (userName != other.userName) return false
        if (likesCount != other.likesCount) return false
        if (commentsCount != other.commentsCount) return false
        if (contentWarning != other.contentWarning) return false

        return true
    }

    override fun hashCode(): Int {
        var result = storyId.hashCode()
        result = 31 * result + storyUrl.hashCode()
        result = 31 * result + (storyThumbUrl?.hashCode() ?: 0)
        result = 31 * result + (storyDescription?.hashCode() ?: 0)
        result = 31 * result + musicCoverTitle.hashCode()
        result = 31 * result + (musicCoverImageLink?.hashCode() ?: 0)
        result = 31 * result + userId.hashCode()
        result = 31 * result + (userProfilePicUrl?.hashCode() ?: 0)
        result = 31 * result + userName.hashCode()
        result = 31 * result + likesCount.hashCode()
        result = 31 * result + commentsCount.hashCode()
        result = 31 * result + (contentWarning?.hashCode() ?: 0)
        return result
    }
}

val DiffUtilsStoriesDataModel = object : DiffUtil.ItemCallback<StoriesDataModel>() {
    override fun areItemsTheSame(oldItem: StoriesDataModel, newItem: StoriesDataModel): Boolean {
        return newItem.storyId == oldItem.storyId
    }

    override fun areContentsTheSame(oldItem: StoriesDataModel, newItem: StoriesDataModel): Boolean {
        return newItem == oldItem
    }
}