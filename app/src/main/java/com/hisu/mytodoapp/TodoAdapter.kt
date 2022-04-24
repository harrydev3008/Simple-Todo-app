package com.hisu.mytodoapp

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
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

            txtTodo.setText(todo.todo)
            txtTodo.imeOptions = EditorInfo.IME_ACTION_DONE
            txtTodo.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txtTodo.requestFocus()

            txtTodo.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
                val imm: InputMethodManager =
                    textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                if (EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {

                    if (txtTodo.text.toString().isEmpty()) {
                        txtTodo.error = "Tell me what you wanna do bro!"
                        return@OnEditorActionListener false
                    }

                    todo.todo = txtTodo.text.toString()
                    txtTodo.clearFocus()

                    imm.hideSoftInputFromWindow(textView.windowToken, 0)
                    return@OnEditorActionListener true
                }

                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                return@OnEditorActionListener false
            })
        }
    }

    override fun getItemCount() = todoList.size

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}