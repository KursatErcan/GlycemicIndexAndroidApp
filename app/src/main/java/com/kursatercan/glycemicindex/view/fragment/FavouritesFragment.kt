package com.kursatercan.glycemicindex.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kursatercan.glycemicindex.databinding.FragmentCategoriesBinding
import com.kursatercan.glycemicindex.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    private var bind : FragmentFavouritesBinding? = null
    private val binding get() = bind!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFavouritesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

}