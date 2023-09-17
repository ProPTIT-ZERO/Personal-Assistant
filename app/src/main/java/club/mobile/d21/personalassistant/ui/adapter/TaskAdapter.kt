package club.mobile.d21.personalassistant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Task

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.task)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val deadlineDate: TextView = itemView.findViewById(R.id.deadlineDay)
        val deadlineTime: TextView = itemView.findViewById(R.id.deadlineTime)
        val detail: TextView = itemView.findViewById(R.id.detail)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.task.text = tasks[position].task
        holder.priority.text = tasks[position].priority.toString()
        holder.deadlineDate.text = tasks[position].deadlineDay.toString()
        holder.deadlineTime.text = tasks[position].deadlineTime.toString()
        holder.detail.text = tasks[position].detail
    }
    override fun getItemCount(): Int {
        return tasks.size
    }
}
