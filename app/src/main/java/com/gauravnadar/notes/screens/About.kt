package com.gauravnadar.notes.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.gauravnadar.notes.R
import com.gauravnadar.notes.databinding.AboutFragmentBinding
import com.gauravnadar.notes.databinding.HomeFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class About : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<AboutFragmentBinding>(inflater, R.layout.about_fragment, container, false)

        binding.back.setOnClickListener {
            view: View ->
            view.findNavController().navigate(R.id.action_about2_to_notesList)
        }

        return binding.root

        }
    }


