package com.caffeine.caffeinenotes.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.caffeinenotes.R
import com.caffeine.caffeinenotes.databinding.NoteItemBinding
import com.caffeine.caffeinenotes.services.model.Notes
import com.caffeine.caffeinenotes.view.fragment.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note : Notes = notesList[position]
        holder.binding.title.text = note.title.toString()
        holder.binding.subtitle.text = note.subtitle.toString()
        holder.binding.description.text = note.description.toString()
        holder.binding.date.text = note.date.toString()

        when (note.priority){
            "1" -> {
                holder.binding.priority.setBackgroundResource(R.drawable.circle_blue)
            }

            "2" -> {
                holder.binding.priority.setBackgroundResource(R.drawable.circle_green)
            }

            "3" -> {
                holder.binding.priority.setBackgroundResource(R.drawable.circle_orange)
            }
        }

        holder.binding.root.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(note)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(val binding : NoteItemBinding) : RecyclerView.ViewHolder(binding.root){}

    fun filterNotes(filteredNotes: ArrayList<Notes>) {
        notesList = filteredNotes
        notifyDataSetChanged()
    }
}