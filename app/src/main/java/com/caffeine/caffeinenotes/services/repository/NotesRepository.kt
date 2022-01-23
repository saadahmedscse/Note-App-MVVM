package com.caffeine.caffeinenotes.services.repository

import androidx.lifecycle.LiveData
import com.caffeine.caffeinenotes.services.dao.NotesDao
import com.caffeine.caffeinenotes.services.model.Notes

class NotesRepository (val dao : NotesDao) {

    fun getNotes() : LiveData<List<Notes>> = dao.getNotes()

    fun getHighNotes() : LiveData<List<Notes>> = dao.getHighNotes()

    fun getMediumNotes() : LiveData<List<Notes>> = dao.getMediumNotes()

    fun getLowNotes() : LiveData<List<Notes>> = dao.getLowNotes()

    fun insertNotes(notes : Notes){
        dao.insertNotes(notes)
    }

    fun deleteNotes (id : Int){
        dao.deleteNotes(id)
    }

    fun updateNotes (notes : Notes){
        dao.updateNotes(notes)
    }
}