package com.gauravnadar.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.gauravnadar.notes.R
import com.gauravnadar.notes.database.NoteDatabase
import com.gauravnadar.notes.databinding.AddNoteFragmentBinding
import com.gauravnadar.notes.viewmodel.NoteViewModel
import com.gauravnadar.notes.viewmodel.NoteViewModelFactory
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AddNote : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<AddNoteFragmentBinding>(
            inflater,
            R.layout.add_note_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val datasource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteViewModelFactory(datasource, application)

        val noteViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)

        binding.noteViewModel = noteViewModel

        binding.setLifecycleOwner(this)


        noteViewModel.title.observe(this, Observer {

            Timber.i("changing")

        })

        noteViewModel.navigateToHomeFragment.observe(this, Observer { data ->
            data?.let {
                Toast.makeText(context, "Note added successfully", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(

                    AddNoteDirections.actionAddNoteToNotesList()
                )
                noteViewModel.onHomeScreen()
            }

        })

        return binding.root

    }
}


