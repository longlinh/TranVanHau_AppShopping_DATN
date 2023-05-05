package com.example.appshoppingdatn.presentation.ui.fragment.report

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentReportBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.example.appshoppingdatn.ultis.Utils
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList

class ReportFragment : BaseFragment<FragmentReportBinding>() {
    private lateinit var viewModel : ReportViewModel
    override fun getLayoutResId(): Int {
        return R.layout.fragment_report
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]
        viewModel.uiEventLiveData.observe(this){
            when(it){
                ReportViewModel.SHOW_MESSAGE_ERROR_CATEGORY -> onShowError()
            }
        }
        onCLickBack()
        viewModel.getDataChart(binding.pieChart,requireActivity())
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.pieChart.visibility = View.VISIBLE
                binding.txtName1.visibility = View.VISIBLE
                binding.txtName2.visibility = View.VISIBLE
                binding.txtName3.visibility = View.VISIBLE
            }
        }

    }

    private fun onCLickBack() {
        binding.imgBackReport.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun onShowError() {
        showMessage(getString(R.string.onShowErrorLoadDataHome))
        viewModel.getDataChart(binding.pieChart,requireActivity())
    }
}