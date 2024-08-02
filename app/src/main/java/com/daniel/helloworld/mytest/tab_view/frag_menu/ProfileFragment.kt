package com.daniel.helloworld.mytest.tab_view.frag_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniel.helloworld.databinding.FragmentProfileBinding


private const val ARG_NAME = "name"
private const val ARG_SCHOOL = "school"

class ProfileFragment : Fragment() {
    private var name: String? = null
    private var school: String? = null

    /*private lateinit var tvName : TextView
    private lateinit var tvSchool : TextView*/

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            school = it.getString(ARG_SCHOOL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        /*val view = inflater.inflate(R.layout.fragment_profile, container, false)
        tvName = view.findViewById(R.id.tv_name)
        tvSchool = view.findViewById(R.id.tv_school)*/
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.text = "Nama: $name"
        binding.tvSchool.text = "Sekolah: $school"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, school: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_SCHOOL, school)
                }
            }
    }
}