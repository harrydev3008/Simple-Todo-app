package com.hisu.mytodoapp

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hisu.mytodoapp.databinding.LayoutTodoItemBinding

class TodoAdapter(
    private var context: Context,
    private var todoList: MutableList<TodoItem> = mutableListOf<TodoItem>()
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutTodoItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false).root
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]

        LayoutTodoItemBinding.bind(holder.itemView).apply {

            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            txtTodo.setText(todo.todo)
            txtTodo.clearFocus()

            txtTodo.imeOptions = EditorInfo.IME_ACTION_DONE
            txtTodo.setRawInputType(InputType.TYPE_CLASS_TEXT)

            if (txtTodo.text.toString().isEmpty()) {
                txtTodo.requestFocus()
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                );
            }

            txtTodo.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
                if (EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {

                    if (txtTodo.text.toString().isEmpty()) {
                        txtTodo.error = "Tell me what you wanna do bro!"
                        return@OnEditorActionListener false
                    }

                    todo.todo = txtTodo.text.toString()

                    notifyItemChanged(position)
                    MySharedPreference(context).putTodo(Gson().toJson(todoList))
                }

                imm.hideSoftInputFromWindow(txtTodo.windowToken, 0)
                txtTodo.clearFocus()
                return@OnEditorActionListener false
            })

            cbxFinish.setOnCheckedChangeListener { button, isChecked ->
                run {
                    if (isChecked)
                        txtTodo.setTextColor(ContextCompat.getColor(context, R.color.color_finish))
                    else
                        txtTodo.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.color_not_finish
                            )
                        )

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (cbxFinish.isChecked) {
                            todoList.removeAt(position)
                            notifyItemRemoved(position)
                            MySharedPreference(context).putTodo(Gson().toJson(todoList))
                        }
                    }, 2 * 1000)
                }
            }
        }
    }

    override fun getItemCount() = todoList.size

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}