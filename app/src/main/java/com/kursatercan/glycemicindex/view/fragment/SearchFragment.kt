package com.kursatercan.glycemicindex.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kursatercan.glycemicindex.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var bind : FragmentSearchBinding? = null
    private val binding get() = bind!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSearchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}