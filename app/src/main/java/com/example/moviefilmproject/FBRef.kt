package com.example.moviefilmproject

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FBRef {
    companion object{
        private val database = Firebase.database

        val contentFBRef = database.getReference("content")
    }
}