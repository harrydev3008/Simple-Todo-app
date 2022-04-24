package com.hisu.mytodoapp

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference(context: Context) {
    private val sharePreference: SharedPreferences =
        context.getSharedPreferences("my_toto_list", Context.MODE_PRIVATE)

    fun putTodo(todo: String) {
        sharePreference.edit().putString("todo", todo).apply()
    }

    fun getTodo() = sharePreference.getString("todo", "")
}