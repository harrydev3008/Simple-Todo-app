package com.hisu.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hisu.mytodoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mMainBinding: ActivityMainBinding
    private lateinit var mTodoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root);

        todoList.add(TodoItem("Wash my pussy"))
        todoList.add(TodoItem("Wash her pussy"))
        todoList.add(TodoItem("Wash his pussy"))

        mTodoAdapter = TodoAdapter(todoList)
        
        mMainBinding.apply {
            todoRecyclerView.adapter = mTodoAdapter
            todoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            todoRecyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }
}