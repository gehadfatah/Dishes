package com.godaMeal.meals.menustags.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.godaMeal.meals.R
import com.godaMeal.meals.base.common.ApplicationIntegration
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.tagsDetails.TagsDetailsActivity
import com.godaMeal.meals.tagsDetails.TagsDetailsActivity.Companion.DETAILS_ITEM
import com.goda.movieapp.util.ConnectionLiveData
import com.godaMeal.meals.base.extensions.showSnakeBar
import com.godaMeal.meals.base.extensions.toggleVisibility
import com.godaMeal.meals.databinding.ActivityMainBinding
import com.godaMeal.meals.databinding.LoadingTransparentBinding
import com.godaMeal.meals.menustags.presentation.adapters.ItemsOfTagsAdapter
import com.godaMeal.meals.menustags.presentation.adapters.TagsAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var itemsOfTagAdapter: ItemsOfTagsAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var loadingTransparentBinding: LoadingTransparentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingTransparentBinding = binding.loadingTrans
        initAdapter()
        initItemsAdapter()
        initObservers()
        setNetworkListner()
    }

    private fun initObservers() {

        viewModel.errorLiveData.observe(this, Observer {
            it?.let {
                binding.rootView.showSnakeBar(it)
            }
        })
        viewModel.showLoadingLiveData.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()

            }
        })
        viewModel.dataloadedLiveData.observe(this) {
            //checkIfNoItem()
            insertToDatabase()

        }

    }

    private fun checkIfNoItem() {


        Handler(Looper.getMainLooper()).postDelayed({

            if (tagsAdapter.snapshot().size == 0) {

                binding.groupContainer.visibility = View.GONE
                binding.noItem.upcomingFeatureLayout.visibility = View.VISIBLE
            } else {
                binding.groupContainer.visibility = View.VISIBLE
                binding.noItem.upcomingFeatureLayout.visibility = View.GONE


            }
        }, 1000)
    }

    private fun onTagsClicked(tagName: String) {
        viewModel.getItemByTagName(tagName)
    }

    private fun setNetworkListner() {
        /* Live data object and setting an observer on it to monitor connection status to update countries  */
        val connectionLiveData =
            ConnectionLiveData(ApplicationIntegration.getApplication())
        connectionLiveData.observe(this, Observer { connection ->
            /* every time connection state changes, we'll be notified and can perform action accordingly */
            if (connection != null && !isDestroyed) {
                if (connection.isConnected) {
                    callFetchTags()
                } else {
                    binding.itemHeader.showSnakeBar(getString(R.string.no_internet_connection))
                    Log.d("d", "onChanged: ")

                }
            }
        })

    }

    private fun initAdapter() {
        with(binding) {
            tagsAdapter = TagsAdapter()
            tagsAdapter.onClick = ::onTagsClicked
            val layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvTags.layoutManager = layoutManager
            rvTags.adapter = tagsAdapter
            callFetchTags()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun callFetchTags() {
        with(binding) {
            lifecycleScope.launch {
                viewModel.fetchTags().collectLatest { pagingData ->
                    lifecycleScope.launch {
                        tagsAdapter.submitData(pagingData)
                    }
                    // insertToDatabase()
                    lifecycleScope.launch {
                        delay(1000)
                        if (tagsAdapter.itemCount < 1)
                            loadDataFromDatabase()
                    }


                }

            }
        }
    }

    private fun insertToDatabase() {

        lifecycleScope.launch {
            //should do some delay
            delay(500)
            if (tagsAdapter.itemCount > 0)
                viewModel.insertTagsToDatabase(tagsAdapter.snapshot().items)
        }
    }

    private fun loadDataFromDatabase() {

        viewModel.getMoviesFromDatabase()
            .observe(this@MainActivity) { listTags ->
                if (listTags.data != null && listTags.data.isNotEmpty()) {
                    lifecycleScope.launch {
                        if (tagsAdapter.itemCount < 1)
                            tagsAdapter.submitData(PagingData.from(listTags.data))
                    }

                }
                checkIfNoItem()


            }
    }


    private fun initItemsAdapter() {
        itemsOfTagAdapter = ItemsOfTagsAdapter() { onItemClicked(it) }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvItemOfTags.layoutManager = layoutManager
        binding.rvItemOfTags.adapter = itemsOfTagAdapter
        viewModel.itemOfTagLiveData.observe(this, Observer<List<ItemOfTags>> {

            itemsOfTagAdapter.submitList(it)

            binding.noItem2.upcomingFeatureLayout.toggleVisibility(it.isEmpty())


        })

    }

    private fun onItemClicked(itemOfTags: ItemOfTags) {
        val bundle = Bundle()
        bundle.putParcelable(DETAILS_ITEM, itemOfTags)
        val intent = Intent(this, TagsDetailsActivity::class.java)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.startActivity(intent)
    }

    private fun showLoading() {
        loadingTransparentBinding.transparentLoadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingTransparentBinding.transparentLoadingView.visibility = View.GONE
    }

}
