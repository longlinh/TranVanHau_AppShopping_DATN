package com.example.appshoppingdatn.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.base.fragment.BaseFragment
import com.example.appshoppingdatn.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_test
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Thực hiện các thao tác với binding ở đây
    }
}