package com.caffeine.caffeinenotes.view.fragment

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.caffeinenotes.R
import com.caffeine.caffeinenotes.databinding.FragmentCreateNotesBinding
import com.caffeine.caffeinenotes.services.model.Notes
import com.caffeine.caffeinenotes.viewmodel.NotesViewModel
import java.util.*

class CreateNotesFragment : Fragment() {

    lateinit var binding : FragmentCreateNotesBinding
    var priority : String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_createNotesFragment_to_homeFragment)
        }

        binding.blue.setOnClickListener(View.OnClickListener {
            priority = "1"
            binding.blue.setImageResource(R.drawable.ic_done)
            binding.orange.setImageResource(0)
            binding.green.setImageResource(0)
        })

        binding.green.setOnClickListener {
            priority = "2"
            binding.green.setImageResource(R.drawable.ic_done)
            binding.orange.setImageResource(0)
            binding.blue.setImageResource(0)
        }

        binding.orange.setOnClickListener{
            priority = "3"
            binding.orange.setImageResource(R.drawable.ic_done)
            binding.blue.setImageResource(0)
            binding.green.setImageResource(0)
        }

        binding.btnSave.setOnClickListener{ view ->
            createNotes(view)
        }

        return binding.root
    }

    private fun createNotes(view: View){
        val title = binding.title.text.toString()
        val subtitle = binding.subtitle.text.toString()
        val description = binding.description.text.toString()
        val d = Date()
        val date : CharSequence = DateFormat.format("dd-MMM-yyyy", d.time)

        val data = Notes(
            null,
            title = title,
            subtitle = subtitle,
            description = description,
            date = date.toString(),
            priority
        )

        when (data.inputValidate()){
            0 -> {
                viewModel.insertNotes(data)
                Toast.makeText(requireContext(), "Note created successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_createNotesFragment_to_homeFragment)
            }

            1 -> {
                binding.title.error = "Define a title"
            }

            2 -> {
                binding.subtitle.error = "Define a subtitle"
            }

            3 -> {
                Toast.makeText(requireContext(), "Note field cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}