package com.example.finalprojectnewsapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectnewsapp.adapter.EverythingAdapter
import com.example.finalprojectnewsapp.adapter.HeadlineAdapter
import com.example.finalprojectnewsapp.data.model.Article
import com.example.finalprojectnewsapp.databinding.ActivityMainBinding
import com.example.finalprojectnewsapp.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<NewsViewModel>()

    private var headLine: ArrayList<Article> = arrayListOf()
    private var everything: ArrayList<Article> = arrayListOf()
    private lateinit var adapter: HeadlineAdapter
    private lateinit var everythingAdapter: EverythingAdapter
    private lateinit var search: String

    private var searchJob: Job? = null
    private val SEARCH_DEBOUNCE_DELAY = 300L

    override fun onStart() {
        super.onStart()
        viewModel.getEverything(viewModel.search.value.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(
            binding.root
        )
        bindViewModels()
        showListNewsHeadline()
        searchViewEverything()
        showListNewsEverything()
        bindEvents()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bindViewModels() {
        viewModel.news.observe(this) { response ->
            when (response) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.tvBreakingNews.isVisible = false
                }

                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.tvBreakingNews.isVisible = true
                    headLine.clear()
                    response.data?.articles?.let { headLine.addAll(it) }
                    Log.d("NewsActivity", "data: $headLine")
                    adapter.notifyDataSetChanged()
                }

                is UIState.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.everything.observe(this) {
            Log.d("NewsActivity", "everything: ${it.data?.articles}")
            when (it) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.textView3.isVisible = false
                }

                is UIState.Success -> {
                    binding.textView3.isVisible = true
                    binding.progressBar.isVisible = false
                    everything.clear()
                    it.data?.articles?.let { it1 -> everything.addAll(it1) }
                }

                is UIState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.search.observe(this) {
            search = it
        }

    }

    private fun bindEvents() {
        everythingAdapter.setOnClickListener(object : EverythingAdapter.OnClickListener {
            override fun onClick(position: Int, model: Article) {
                val intent = Intent(this@NewsActivity, DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("article", model)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
    }

    private fun showListNewsHeadline() {
        adapter = HeadlineAdapter(headLine)
        binding.rvHeadline.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHeadline.setHasFixedSize(true)
        binding.rvHeadline.adapter = adapter
    }

    private fun showListNewsEverything() {
        everythingAdapter = EverythingAdapter(everything)

        binding.rvEverything.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEverything.setHasFixedSize(true)
        binding.rvEverything.adapter = everythingAdapter
    }

    private fun searchViewEverything() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                binding.searchView.isIconified = true
                everything.clear()
                viewModel.getEverything(query ?: search)
                everythingAdapter.notifyDataSetChanged()
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchJob?.cancel()
                    searchJob = CoroutineScope(IO).launch {
                        delay(SEARCH_DEBOUNCE_DELAY)
                        viewModel.getEverything(newText.trim())
                    }
                }
                everythingAdapter.notifyDataSetChanged()
                return false
            }

        })
    }
}
