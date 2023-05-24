package com.example.mvipattern.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvipattern.adapter.ItemAdapter
import com.example.mvipattern.databinding.ActivityApiBinding
import com.example.mvipattern.intents.APIIntents
import com.example.mvipattern.intents.MainIntents
import com.example.mvipattern.models.EntriesResponse
import com.example.mvipattern.viewmodel.APIViewModel
import com.example.mvipattern.viewstates.APIViewState
import kotlinx.coroutines.launch

class APIActivity : AppCompatActivity() {
    private val TAG = APIActivity::class.java.name

    private lateinit var binding: ActivityApiBinding
    private val viewModel: APIViewModel by lazy {
        ViewModelProvider(this)[APIViewModel::class.java]
    }
    private lateinit var itemsAdapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.intentChannel.send(APIIntents.RequestData)
        }

        initRecyclerView()
        render()
    }

    private fun initRecyclerView() {
        itemsAdapter = ItemAdapter()
        binding.itemsRecyclerView.apply {
            val customLayoutManager = LinearLayoutManager(this@APIActivity)
            customLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = customLayoutManager
            adapter = itemsAdapter
        }
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                when (state) {
                    is APIViewState.Loading -> {
                        Log.i(TAG, "render: Loading ----> $state")
                        binding.progressBar.visibility = View.VISIBLE
                        binding.countTextView.visibility = View.GONE
                        binding.itemsRecyclerView.visibility = View.GONE
                    }

                    is APIViewState.Success<*> -> {
                        Log.i(TAG, "render: Success ----> $state")
                        when (state.result) {
                            is EntriesResponse -> {
                                binding.countTextView.visibility = View.VISIBLE
                                binding.itemsRecyclerView.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                itemsAdapter.setDataSource(state.result.values)
                                binding.countTextView.text = state.result.count.toString()
                            }
                        }
                    }

                    is APIViewState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.countTextView.visibility = View.VISIBLE
                        binding.itemsRecyclerView.visibility = View.GONE
                        binding.countTextView.text = state.error
                        Log.i(TAG, "render: Error ----> $state")
                    }
                }
            }
        }
    }
}