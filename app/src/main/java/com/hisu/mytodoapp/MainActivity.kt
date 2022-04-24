package com.hisu.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hisu.mytodoapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mMainBinding: ActivityMainBinding
    private lateinit var mTodoAdapter: TodoAdapter
    private var todoList = mutableListOf<TodoItem>()
    private lateinit var sharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root);
        sharedPreference = MySharedPreference(this)

        getTodoList(sharedPreference.getTodo())
        mTodoAdapter = TodoAdapter(this, todoList)

        val simpleCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                Collections.swap(todoList, from, to);
                mTodoAdapter.notifyItemMoved(from, to)

                sharedPreference.putTodo(Gson().toJson(todoList))

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)

        mMainBinding.apply {
            todoRecyclerView.adapter = mTodoAdapter
            todoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            itemTouchHelper.attachToRecyclerView(todoRecyclerView)

            txtNewReminder.setOnClickListener {
                todoList.add(TodoItem())
                mTodoAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getTodoList(json: String?) {
        if (json == null || json.isEmpty()) return
        todoList =
            Gson().fromJson(sharedPreference.getTodo(), Array<TodoItem>::class.java).toMutableList()
    }
}