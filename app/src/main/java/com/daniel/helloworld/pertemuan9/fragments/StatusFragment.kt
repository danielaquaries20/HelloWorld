package com.daniel.helloworld.pertemuan9.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daniel.helloworld.R


private const val NAME = "nama"
private const val STATUS = "status"

class StatusFragment : Fragment() {
    private var name: String? = null
    private var status: String? = null

    private lateinit var tvName: TextView
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME)
            status = it.getString(STATUS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status, container, false)
        tvName = view.findViewById(R.id.tv_name)
        tvStatus = view.findViewById(R.id.tv_status)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = "Nama: $name"
        tvStatus.text = "Status: $status"
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, status: String) =
            StatusFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    putString(STATUS, status)
                }
            }
    }
}