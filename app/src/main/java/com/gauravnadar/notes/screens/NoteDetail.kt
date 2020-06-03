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
import androidx.navigation.fragment.navArgs
import com.gauravnadar.notes.R
import com.gauravnadar.notes.database.NoteDatabase
import com.gauravnadar.notes.databinding.NoteDetailFragmentBinding
import com.gauravnadar.notes.viewmodel.NoteDetailViewModel
import com.gauravnadar.notes.viewmodel.NoteDetailViewModelFactory
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class NoteDetail : Fragment() {

    val args: NoteDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<NoteDetailFragmentBinding>(inflater, R.layout.note_detail_fragment, container, false)

        val note_id = args.id
        Timber.i("${note_id}")

        val application = requireNotNull(this.activity).application

        val datasource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteDetailViewModelFactory(note_id, datasource)

        val noteDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteDetailViewModel::class.java)

        binding.noteDetailViewModel = noteDetailViewModel

        noteDetailViewModel.note_detail.observe(this, Observer {
            Timber.i("${it.title}")
            binding.dDate.text = it.date
            binding.dTime.text = it.time
            binding.dTitle.setText(it.title)
            binding.dTitle.setSelection(binding.dTitle.text.length)
            binding.dNote.setText(it.note)
            binding.dNote.setSelection(binding.dNote.text.length)
        })


        noteDetailViewModel.update_flag.observe(this, Observer { update ->
            update?.let {
                Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
            }

        })

        return binding.root
    }

}
