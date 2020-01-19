package pl.mbachorski.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import pl.mbachorski.roomwordsamplecodelab.TodoItemsListAdapter
import pl.mbachorski.roomwordsamplecodelab.TodoListViewModel
import pl.mbachorski.todolist.todos.NewTodoItemActivity

class MainActivity : AppCompatActivity() {

    private lateinit var todoListViewModel: TodoListViewModel
    private val newTodoItemActivityRequestCode = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        todoListViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        setSupportActionBar(toolbar)
        initList()
        initAddButton()
    }

    private fun initList() {
        val recyclerView = findViewById<RecyclerView>(R.id.todo_list_recyclerview)
        val adapter = TodoItemsListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoListViewModel.allTodoItems.observe(this, Observer { todoItems ->
            todoItems?.let { adapter.setTodoItems(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAddButton() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewTodoItemActivity::class.java)
            startActivityForResult(intent, newTodoItemActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newTodoItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewTodoItemActivity.EXTRA_REPLY)?.let {
                todoListViewModel.insert(it)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
