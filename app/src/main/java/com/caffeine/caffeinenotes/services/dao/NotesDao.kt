package com.caffeine.caffeinenotes.services.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.caffeine.caffeinenotes.services.model.Notes

@Dao
interface NotesDao {

    @Query ("SELECT * FROM Notes ORDER BY id DESC")
    fun getNotes() : LiveData<List<Notes>>

    @Query ("SELECT * FROM Notes WHERE priority=1 ORDER BY id DESC")
    fun getHighNotes() : LiveData<List<Notes>>

    @Query ("SELECT * FROM Notes WHERE priority=2 ORDER BY id DESC")
    fun getMediumNotes() : LiveData<List<Notes>>

    @Query ("SELECT * FROM Notes WHERE priority=3 ORDER BY id DESC")
    fun getLowNotes() : LiveData<List<Notes>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes (notes : Notes)

    @Query ("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes (id : Int)

    @Update
    fun updateNotes (notes : Notes)
}