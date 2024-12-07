package com.daniel.helloworld.mytest.mahasiswa.ui.btm_sht

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.helloworld.databinding.BtmShtFilterTestBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TestBtmShtFilter(
    private val onApply: (filterBy: String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BtmShtFilterTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BtmShtFilterTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApply.setOnClickListener {
            val selectedFilter = when (binding.filterOptions.checkedRadioButtonId) {
                binding.filterFurniture.id -> "furniture"
                binding.filterLaptops.id -> "laptops"
                binding.filterMotor.id -> "motorcycle"
                binding.filterTops.id -> "tops"
                binding.filterVehicle.id -> "vehicle"
                else -> ""
            }
            onApply(selectedFilter)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}