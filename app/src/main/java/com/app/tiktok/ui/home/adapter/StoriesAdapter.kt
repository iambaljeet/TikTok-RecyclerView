package com.app.tiktok.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tiktok.databinding.ListItemStoryViewBinding
import com.app.tiktok.model.DiffUtilsStoriesDataModel
import com.app.tiktok.model.StoriesDataModel
import com.app.tiktok.utils.formatNumberAsReadableFormat
import com.app.tiktok.utils.loadCenterCropImageFromUrl
import com.app.tiktok.utils.setTextOrHide

class StoriesAdapter : ListAdapter<StoriesDataModel, StoriesViewHolder>(DiffUtilsStoriesDataModel) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(
            ListItemStoryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class StoriesViewHolder(private val binding: ListItemStoryViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val viewBinding: ListItemStoryViewBinding = binding

    fun bind(storiesDataModel: StoriesDataModel) {
        with(binding.layoutStoryView) {
            textViewAccountHandle.setTextOrHide(value = storiesDataModel.userName)
            textViewVideoDescription.setTextOrHide(value = storiesDataModel.storyDescription)
            textViewMusicTitle.setTextOrHide(value = storiesDataModel.musicCoverTitle)

            imageViewOptionCommentTitle.text =
                storiesDataModel.commentsCount.formatNumberAsReadableFormat()
            imageViewOptionLikeTitle.text =
                storiesDataModel.likesCount.formatNumberAsReadableFormat()

            imageViewProfilePic.loadCenterCropImageFromUrl(storiesDataModel.userProfilePicUrl)

            textViewMusicTitle.isSelected = true
        }
    }
}