package com.gmail.newstestingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.newstestingapp.R
import com.gmail.newstestingapp.databinding.FragmentFavouritesBinding
import com.gmail.newstestingapp.databinding.FragmentStoriesBinding
import com.gmail.newstestingapp.viewmodel.FavouritesViewModel
import com.gmail.newstestingapp.viewmodel.StoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private lateinit var binding: FragmentFavouritesBinding
    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        setUpNews()

        return binding.root
    }

    private fun setUpNews() {
        val adapter = NewsAdapter()
        binding.favouritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favouritesRecyclerView.adapter = adapter
        (binding.favouritesRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false

        observeUsers(adapter)
    }

    private fun observeUsers(adapter: NewsAdapter) {
        lifecycleScope.launch {
            viewModel.newsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}