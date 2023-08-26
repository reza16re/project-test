package com.example.mvgparchitect.mvp.model

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mvgparchitect.db.DBHandler
import com.example.mvgparchitect.db.model.TaskEntity
import com.example.mvgparchitect.mvp.ext.OnBindData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModelMainActivity(private val activity: AppCompatActivity) {

    private val db = DBHandler.getDatabase(activity)

    fun setData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().insertTask(taskEntity)
            }
        }
    }

    fun editData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().updateTasks(taskEntity)
            }
        }
    }

    fun getData(state: Boolean,onBindData: OnBindData) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val tasks = db.taskDao().getTasksByColumn(state)
                withContext(Dispatchers.Main) {
                    tasks.collect {
                        onBindData.getData(it)
                    }
                }
            }
        }
    }

    fun deleteData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().deleteTasks(taskEntity)
            }
        }
    }

    fun closeDatabase() {
        db.close()
    }

}