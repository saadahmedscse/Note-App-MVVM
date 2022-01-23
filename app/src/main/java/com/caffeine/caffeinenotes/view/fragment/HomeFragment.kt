package com.caffeine.caffeinenotes.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.caffeine.caffeinenotes.R
import com.caffeine.caffeinenotes.databinding.FragmentHomeBinding
import com.caffeine.caffeinenotes.services.model.Notes
import com.caffeine.caffeinenotes.view.adapter.NotesAdapter
import com.caffeine.caffeinenotes.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()
    var oldNotes = arrayListOf<Notes>()
    lateinit var adapter : NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            getNote(requireContext(), notesList)
        })

        binding.btnAdd.setOnClickListener{ view ->
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.all.setOnClickListener{
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })
        }

        binding.high.setOnClickListener{
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })
        }

        binding.medium.setOnClickListener{
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })
        }

        binding.low.setOnClickListener{
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList ->
                getNote(requireContext(), notesList)
            })
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterNotes(s.toString().lowercase())
            }
        })

        return binding.root
    }

    private fun getNote(context: Context, notesList: List<Notes>?) {
        oldNotes = notesList as ArrayList<Notes>
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        adapter = NotesAdapter(context, notesList)
        binding.recyclerView.adapter = adapter

        binding.noNote.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun filterNotes(txt: String) {
        val filteredNotes = arrayListOf<Notes>()

        for (item in oldNotes){
            if (item.title.lowercase().contains(txt) || item.subtitle.lowercase().contains(txt)){
                filteredNotes.add(item)
            }
        }
        binding.noNote.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        adapter.filterNotes(filteredNotes)
    }

}