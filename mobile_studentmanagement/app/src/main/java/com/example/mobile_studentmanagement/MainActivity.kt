package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listViewStudents: ListView
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewStudents = findViewById(R.id.list_view_students)
        val btnAddNew: FloatingActionButton = findViewById(R.id.btn_add_new)

        studentAdapter = StudentAdapter(this, students)
        listViewStudents.adapter = studentAdapter

        // Đăng ký context menu
        registerForContextMenu(listViewStudents)

        // Thêm mới sinh viên
        btnAddNew.setOnClickListener {
            val intent = Intent(this, AddOrEditStudentActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            val intent = Intent(this, AddOrEditStudentActivity::class.java)
            startActivityForResult(intent, 1)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        when (item.itemId) {
            R.id.menu_edit -> {
                val intent = Intent(this, AddOrEditStudentActivity::class.java)
                intent.putExtra("student", students[position])
                intent.putExtra("position", position)
                startActivityForResult(intent, 2)
            }
            R.id.menu_remove -> {
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as Student
            if (requestCode == 1) { // Add new student
                students.add(student)
            } else if (requestCode == 2) { // Edit student
                val position = data.getIntExtra("position", -1)
                if (position != -1) {
                    students[position] = student
                }
            }
            studentAdapter.notifyDataSetChanged()
        }
    }
}
