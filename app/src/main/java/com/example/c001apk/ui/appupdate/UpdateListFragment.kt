package com.example.c001apk.ui.appupdate

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.absinthe.libraries.utils.extensions.dp
import com.example.c001apk.adapter.HeaderAdapter
import com.example.c001apk.databinding.FragmentHomeFeedBinding
import com.example.c001apk.logic.model.UpdateCheckResponse
import com.example.c001apk.ui.base.BaseFragment
import com.example.c001apk.view.LinearItemDecoration
import com.example.c001apk.view.StaggerItemDecoration

class UpdateListFragment : BaseFragment<FragmentHomeFeedBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[UpdateListViewModel::class.java] }
    private lateinit var mAdapter: UpdateListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var sLayoutManager: StaggeredGridLayoutManager
    private lateinit var appsUpdateList: List<UpdateCheckResponse.Data>

    companion object {
        @JvmStatic
        fun newInstance(appsUpdateList: ArrayList<UpdateCheckResponse.Data>) =
            UpdateListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("list", appsUpdateList)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            appsUpdateList =
                it.getParcelableArrayList<UpdateCheckResponse.Data>("list") as List<UpdateCheckResponse.Data>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRefresh()
        initObserve()

    }

    private fun initObserve() {
        viewModel.doNext.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                try {
                    requireContext().startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(requireContext(), "打开失败", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun initRefresh() {
        binding.swipeRefresh.isEnabled = false
    }

    private fun initView() {
        mAdapter = UpdateListAdapter(appsUpdateList, viewModel)
        binding.recyclerView.apply {
            adapter = ConcatAdapter(HeaderAdapter(), mAdapter)
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mLayoutManager = LinearLayoutManager(requireContext())
                    mLayoutManager
                } else {
                    sLayoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    sLayoutManager
                }
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                addItemDecoration(LinearItemDecoration(10.dp))
            else
                addItemDecoration(StaggerItemDecoration(10.dp))
        }
    }

    override fun onResume() {
        super.onResume()
        initLift()
    }

    override fun onStart() {
        super.onStart()
        initLift()
    }

    private fun initLift() {
        val parent = requireActivity() as AppUpdateActivity
        parent.binding.appBar.setLifted(
            !binding.recyclerView.borderViewDelegate.isShowingTopBorder
        )
        binding.recyclerView.borderViewDelegate
            .setBorderVisibilityChangedListener { top, _, _, _ ->
                parent.binding.appBar.setLifted(!top)
            }
    }

    override fun onStop() {
        super.onStop()
        detachLift()
    }

    override fun onPause() {
        super.onPause()
        detachLift()
    }

    private fun detachLift() {
        binding.recyclerView.borderViewDelegate.borderVisibilityChangedListener = null
    }

}