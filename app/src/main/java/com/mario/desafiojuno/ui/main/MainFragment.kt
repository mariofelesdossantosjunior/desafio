package com.mario.desafiojuno.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mario.desafiojuno.R
import com.mario.desafiojuno.data.local.entity.Item
import com.mario.desafiojuno.data.local.entity.Result
import com.mario.desafiojuno.ui.detail.DetailFragment
import com.mario.desafiojuno.util.replaceFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment(),
    SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val viewModel by viewModel<MainViewModel>()

    private val adapter: ResultAdapter by lazy {
        ResultAdapter {
            openDetail(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecycle()
        setupObservable()
        loadCache()
    }

    private fun loadCache() {
        if(viewModel.isCached()){
            viewModel.findAllResult()
        }else{
            search("[android]")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecycle() {
        rcResults.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcResults.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rcResults.setHasFixedSize(true)
        rcResults.adapter = adapter
    }

    private fun setupObservable() {
        viewModel.results().observe(this, Observer { it?.let { showResults(it) } })
        viewModel.loading().observe(this, Observer { it?.let { showProgress(it) } })
        viewModel.error().observe(this, Observer { it?.let { showError(it) } })
    }

    private fun showResults(result: Result) {
        adapter.clearItem()
        adapter.addItems(result.items)
    }

    fun showProgress(show: Boolean) {
        progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query.toString())
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun search(query: String) {
        if (query.isNotBlank()) disposable.add(viewModel.searchResult(query))
    }

    private fun openDetail(it: Item) {
        activity?.supportFragmentManager?.replaceFragment(DetailFragment().apply {
            arguments = bundleOf("ITEM" to it)
        }, R.id.container)
    }


    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
