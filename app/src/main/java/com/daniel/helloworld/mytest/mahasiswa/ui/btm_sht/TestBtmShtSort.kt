package com.daniel.helloworld.mytest.mahasiswa.ui.btm_sht

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.helloworld.databinding.BtmShtSortTestBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TestBtmShtSort(
    private val onApply: (sortBy: String, orderBy: String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BtmShtSortTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BtmShtSortTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApply.setOnClickListener {
            val selectedSort = when (binding.sortOptions.checkedRadioButtonId) {
                binding.sortTitle.id -> "title"
                binding.sortDesc.id -> "description"
                binding.sortId.id -> "id"
                else -> ""
            }

            val selectedOrder = when (binding.orderOptions.checkedRadioButtonId) {
                binding.desc.id -> "desc"
                binding.asc.id -> "asc"
                else -> ""
            }
            onApply(selectedSort, selectedOrder)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}