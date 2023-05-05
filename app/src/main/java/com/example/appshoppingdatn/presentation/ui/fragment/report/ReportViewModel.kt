package com.example.appshoppingdatn.presentation.ui.fragment.report
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.data.api.APIService
import com.example.appshoppingdatn.data.api.RetrofitClient
import com.example.appshoppingdatn.model.ProductModel
import com.example.appshoppingdatn.presentation.ui.base.SingleLiveData
import com.example.appshoppingdatn.presentation.ui.base.viewmodel.BaseViewModel
import com.example.appshoppingdatn.ultis.Utils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import com.github.mikephil.charting.utils.ColorTemplate

class ReportViewModel : BaseViewModel() {
    val uiEventLiveData = SingleLiveData<Int>()
    private  var apiService : APIService?= null
    private var compositeDisposable = CompositeDisposable()
    var listProductModel = MutableLiveData<ProductModel>()


    companion object{
        const val SHOW_MESSAGE_ERROR_CATEGORY = 1
    }
    init {
        apiService = RetrofitClient.instance.create(APIService::class.java)
    }

    fun getDataChart(pieChart : PieChart,context: Context){
        isLoading.value = true
        compositeDisposable.addAll(apiService!!.getReport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                productModel ->
                run {
                    isLoading.value = false
                    if (productModel.success){
                        if (productModel.result != null){
                            listProductModel.value = ProductModel(productModel.success,productModel.message,productModel.result)
                            Utils.listProductModel = productModel
                            val listData = ArrayList<PieEntry>()
                            listData.clear()
                            for (i in 0 until productModel.result.size) {
                                val nameSP = productModel.result[i].NameProduct
                                val sell = productModel.result[i].SelledProduct
                                listData.add(PieEntry(sell.toFloat(), nameSP))
                            }
                            val pieDataSet = PieDataSet(listData, "")
                            val data = PieData()
                            data.dataSet = pieDataSet
                            data.setValueTextSize(15f)
                            data.setValueFormatter(PercentFormatter(pieChart))
                            pieDataSet.setColors(
                                *intArrayOf(
                                    context.resources.getColor(R.color.background2),
                                    context.resources.getColor(R.color.colorYellow),
                                    context.resources.getColor(R.color.tulip)
                                )
                            )
                            pieChart.data = data
                            pieChart.animateXY(2000, 200)
                            pieChart.setUsePercentValues(true)
                            pieChart.description.isEnabled = false
                            pieChart.legend.isEnabled = false // ẩn ô màu
                            pieChart.invalidate()
                        }
                    }
                }
            },{
              uiEventLiveData.value = SHOW_MESSAGE_ERROR_CATEGORY
            })
        )
    }
}