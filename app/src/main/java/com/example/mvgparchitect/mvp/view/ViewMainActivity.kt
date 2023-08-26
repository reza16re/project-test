package com.example.mvgparchitect.mvp.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvgparchitect.adapter.RecyclerTaskAdapter
import com.example.mvgparchitect.databinding.ActivityMainBinding
import com.example.mvgparchitect.databinding.CustomDialogBinding
import com.example.mvgparchitect.db.model.TaskEntity
import com.example.mvgparchitect.mvp.ext.OnBindData

class ViewMainActivity(
    contextInstance: Context
) : FrameLayout(contextInstance) {

    val binding =
        ActivityMainBinding.inflate(LayoutInflater.from(context))

    private lateinit var adapter:RecyclerTaskAdapter

    fun showDialog(onBindData: OnBindData) {
        binding.fab.setOnClickListener {
            val view = CustomDialogBinding.inflate(LayoutInflater.from(context))
            val dialog = Dialog(context)
            dialog.setContentView(view.root)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            view.btnCancel.setOnClickListener { dialog.dismiss() }
            view.btnSave.setOnClickListener {
                val text = view.edtTask.text.toString()
                if (text.isNotEmpty()) {
                    onBindData.saveData(TaskEntity(title = text, state = false))
                    Toast.makeText(context, "وظیفه شما ایجاد شد", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                } else {
                    Toast.makeText(context, "لطفا وظیفه را وارد کنید", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showTask(tasks:List<TaskEntity>){
        val data = arrayListOf<TaskEntity>()
        tasks.forEach { data.add(it) }
        adapter.dataUpdate(data)
    }

    fun initRecycler(tasks:ArrayList<TaskEntity>, onBindData: OnBindData){
        adapter = RecyclerTaskAdapter(tasks,onBindData)
        binding.recyclerView.layoutManager=
            LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.recyclerView.adapter = adapter
    }
    fun setData(onBindData: OnBindData){
        onBindData.requestData(false)

        binding.rdbTrue.setOnClickListener {
            onBindData.requestData(true)
        }
        binding.rdbFalse.setOnClickListener {
            onBindData.requestData(false)
        }
    }

}