package com.gauravnadar.notes.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gauravnadar.notes.R
import com.gauravnadar.notes.database.NoteDatabase
import com.gauravnadar.notes.databinding.HomeFragmentBinding
import com.gauravnadar.notes.util.NoteListener
import com.gauravnadar.notes.util.NotesRecyclerAdapter
import com.gauravnadar.notes.viewmodel.NoteViewModel
import com.gauravnadar.notes.viewmodel.NoteViewModelFactory
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class NotesList : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false)
        binding.addNote.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_notesList_to_addNote)
        }


        val application = requireNotNull(this.activity).application

        val datasource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteViewModelFactory(datasource, application)
        val noteViewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)

        val adapter = NotesRecyclerAdapter(NoteListener { noteId ->

            Timber.i( "${noteId}")

            noteViewModel.onNoteClicked(noteId)

        })

        binding.deleteAll.setOnClickListener { view: View ->

            AlertDialog.Builder(context)
                .setTitle("Delete All")
                .setMessage("Are you sure you want to delete all notes?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                    noteViewModel.deletefull()

                })
                .setNegativeButton("No", null)
                .show()
        }


        binding.notesList.adapter = adapter

        noteViewModel.notes.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)
        }
        )


        noteViewModel.navigateToDetailFragment.observe(this, Observer { note ->
            note?.let {
                this.findNavController().navigate(

                    NotesListDirections.actionNotesListToNoteDetail(note)
                )
                noteViewModel.onDetailFragmentNavigated()
            }
        })


        noteViewModel.navigateToHomeFragment.observe(this, Observer {
            data ->
            Toast.makeText(context, "${data}", Toast.LENGTH_SHORT).show()
            noteViewModel.onNoteEntered()
        })


        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getNoteAt(viewHolder.adapterPosition)?.let {
                    noteViewModel.deleteSingleNote(
                        it
                    )
                }
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.notesList)


        setHasOptionsMenu(true)
        return binding.root


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.app_menu_bar, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.about)
        {
            this.findNavController().navigate(R.id.action_notesList_to_about2)

            return true
        }else
        {
        return false
        }
    }


}
