package com.hisu.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        mTodoAdapter = TodoAdapter(todoList)

        mMainBinding.apply {
            todoRecyclerView.adapter = mTodoAdapter
            todoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            txtNewReminder.setOnClickListener {
                todoList.add(TodoItem())
                mTodoAdapter.notifyDataSetChanged()
            }
        }
    }
}