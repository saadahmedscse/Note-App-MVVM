package com.caffeine.caffeinenotes.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.caffeine.caffeinenotes.R
import com.caffeine.caffeinenotes.databinding.FragmentHomeBinding
import com.caffeine.caffeinenotes.services.model.Notes
import com.caffeine.caffeinenotes.view.activity.HostActivity
import com.caffeine.caffeinenotes.view.adapter.NotesAdapter
import com.caffeine.caffeinenotes.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : NotesViewModel by viewModels()
    private var oldNotes = arrayListOf<Notes>()
    private lateinit var adapter : NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            getNote(requireContext(), notesList)
        }

        binding.btnAdd.setOnClickListener{ view ->
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.all.setOnClickListener{
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                getNote(requireContext(), notesList)
            }

            changeButtons(
                R.color.colorBlack, R.drawable.bg_yellow_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        binding.high.setOnClickListener{
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                getNote(requireContext(), notesList)
            }

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorBlack, R.drawable.bg_blue_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        binding.medium.setOnClickListener{
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                getNote(requireContext(), notesList)
            }

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorBlack, R.drawable.bg_green_25,
                R.color.colorWhite, R.drawable.bg_orange_stroke_25
            )
        }

        binding.low.setOnClickListener{
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                getNote(requireContext(), notesList)
            }

            changeButtons(
                R.color.colorWhite, R.drawable.bg_yellow_stroke_25,
                R.color.colorWhite, R.drawable.bg_blue_stroke_25,
                R.color.colorWhite, R.drawable.bg_green_stroke_25,
                R.color.colorBlack, R.drawable.bg_orange_25
            )
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

    private fun changeButtons(allColor : Int, allBg : Int,
                              blueColor : Int, blueBg : Int,
                              greenColor : Int, greenBg : Int,
                              orangeColor : Int, orangeBg : Int){
        binding.all.setTextColor(resources.getColor(allColor))
        binding.all.setBackgroundResource(allBg)
        binding.high.setTextColor(resources.getColor(blueColor))
        binding.high.setBackgroundResource(blueBg)
        binding.medium.setTextColor(resources.getColor(greenColor))
        binding.medium.setBackgroundResource(greenBg)
        binding.low.setTextColor(resources.getColor(orangeColor))
        binding.low.setBackgroundResource(orangeBg)
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

    override fun onResume() {
        super.onResume()
        (activity as HostActivity).updateCount(0)
    }
}