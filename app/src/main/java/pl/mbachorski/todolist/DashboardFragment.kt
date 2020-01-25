package pl.mbachorski.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import pl.mbachorski.todolist.todos.NewTodoItemActivity
import pl.mbachorski.todolist.todos.TodoItemsListAdapter
import pl.mbachorski.todolist.todos.TodoListViewModel


class DashboardFragment : Fragment() {

    private val doneClickSubject = PublishSubject.create<Int>()
    private lateinit var todoListViewModel: TodoListViewModel
    private val disposable = CompositeDisposable()
    private val newTodoItemActivityRequestCode = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoListViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initAddButton(view)
    }

    private fun initList() {
        disposable.addAll(doneClickSubject.subscribe { next ->
            todoListViewModel.deleteById(next)
        })

        activity?.let {
            val recyclerView = it.findViewById<RecyclerView>(R.id.todo_list_recyclerview)
            val adapter = TodoItemsListAdapter(it, doneClickSubject)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it)
            todoListViewModel.allTodoItems.observe(this, Observer { todoItems ->
                todoItems?.let { items -> adapter.setTodoItems(items) }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newTodoItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewTodoItemActivity.EXTRA_REPLY)?.let {
                todoListViewModel.insert(it)
            }
        } else {
            activity?.let {
                Toast.makeText(
                    it.applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initAddButton(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(requireActivity(), NewTodoItemActivity::class.java)
            startActivityForResult(intent, newTodoItemActivityRequestCode)
        }
    }
}
