package com.app.tiktok.ui.discover.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.tiktok.base.BaseFragment
import com.app.tiktok.databinding.FragmentDiscoverBinding
import com.app.tiktok.ui.discover.viewmodel.DiscoverViewModel

class DiscoverFragment : BaseFragment() {
    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var viewModel: DiscoverViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverBinding.inflate(inflater)
        return binding.root
    }
}