package com.hisu.mytodoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hisu.mytodoapp.databinding.LayoutTodoItemBinding

class TodoAdapter(private var todoList: List<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutTodoItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false).root
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        LayoutTodoItemBinding.bind(holder.itemView).apply {
            txtTodo.text = todo.todo
        }
    }

    override fun getItemCount() = todoList.size

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}