package com.example.studentmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StudentAdapter(private val context: Context, private val students: List<Student>) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        val student = students[position]

        val textName: TextView = view.findViewById(R.id.text_student_name)
        val textId: TextView = view.findViewById(R.id.text_student_id)

        textName.text = student.name
        textId.text = student.id

        return view
    }
}
