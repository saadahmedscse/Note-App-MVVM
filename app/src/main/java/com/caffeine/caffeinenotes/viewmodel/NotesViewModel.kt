package com.caffeine.caffeinenotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.caffeine.caffeinenotes.services.database.NotesDatabase
import com.caffeine.caffeinenotes.services.model.Notes
import com.caffeine.caffeinenotes.services.repository.NotesRepository

class NotesViewModel (application : Application) : AndroidViewModel(application) {

    val repository : NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun insertNotes (notes : Notes){
        repository.insertNotes(notes)
    }

    fun getNotes() : LiveData<List<Notes>> {
        return repository.getNotes()
    }

    fun updateNotes (notes : Notes) {
        repository.updateNotes(notes)
    }

    fun deleteNotes (id : Int) {
        repository.deleteNotes(id)
    }

    fun getHighNotes() : LiveData<List<Notes>> = repository.getHighNotes()

    fun getMediumNotes() : LiveData<List<Notes>> = repository.getMediumNotes()

    fun getLowNotes() : LiveData<List<Notes>> = repository.getLowNotes()
}