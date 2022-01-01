package com.app.tiktok.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.tiktok.base.BaseFragment
import com.app.tiktok.databinding.FragmentHomeBinding
import com.app.tiktok.model.ResultData
import com.app.tiktok.model.StoriesDataModel
import com.app.tiktok.ui.home.adapter.StoriesAdapter
import com.app.tiktok.ui.main.viewmodel.MainViewModel
import com.app.tiktok.utils.Constants
import com.app.tiktok.work.PreCachingService
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModel<MainViewModel>()

    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            storiesAdapter = StoriesAdapter()
            recyclerViewStories.adapter = storiesAdapter

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(recyclerViewStories)

            homeViewModel.videoListLiveData.observe(viewLifecycleOwner) { value ->
                when (value) {
                    is ResultData.Loading -> {
                    }
                    is ResultData.Success -> {
                        if (!value.data.isNullOrEmpty()) {
                            val dataList = value.data
                            recyclerViewStories.submitList(dataList)

                            startPreCaching(dataList)
                        }
                    }
                    is ResultData.Failed -> {
                    }
                    is ResultData.Exception -> {
                    }
                }
            }
        }
    }

    private fun startPreCaching(dataList: ArrayList<StoriesDataModel>) {
        val urlList = arrayOfNulls<String>(dataList.size)
        dataList.mapIndexed { index, storiesDataModel ->
            urlList[index] = storiesDataModel.storyUrl
        }
        val inputData =
            Data.Builder().putStringArray(Constants.KEY_STORIES_LIST_DATA, urlList).build()
        val preCachingWork = OneTimeWorkRequestBuilder<PreCachingService>().setInputData(inputData)
            .build()
        WorkManager.getInstance(requireContext())
            .enqueue(preCachingWork)
    }

    override fun onDestroyView() {
        with(binding) {
            recyclerViewStories.onDestroy()
        }
        super.onDestroyView()
    }
}