package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddOrEditStudentActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit_student)

        editTextName = findViewById(R.id.edit_text_name)
        editTextId = findViewById(R.id.edit_text_id)
        btnSave = findViewById(R.id.btn_save)

        val student = intent.getSerializableExtra("student") as? Student
        val position = intent.getIntExtra("position", -1)

        student?.let {
            editTextName.setText(it.name)
            editTextId.setText(it.id)
        }

        btnSave.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()

            val newStudent = Student(name, id)

            val resultIntent = Intent()
            resultIntent.putExtra("student", newStudent)
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
