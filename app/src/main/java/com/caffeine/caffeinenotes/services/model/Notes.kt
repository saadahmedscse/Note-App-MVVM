package com.caffeine.caffeinenotes.services.model

import android.content.Context
import android.os.Parcelable
import android.text.TextUtils
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "Notes")
@Parcelize
class Notes (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var title : String,
    var subtitle : String,
    var description : String,
    var date : String,
    var priority : String
) : Parcelable {
    fun inputValidate() : Int{
        if (TextUtils.isEmpty(title)){
            return 1
        }

        else if (TextUtils.isEmpty(subtitle)){
            return 2
        }

        else if (TextUtils.isEmpty(description)){
            return 3
        }

        else{
            return 0
        }
    }
}