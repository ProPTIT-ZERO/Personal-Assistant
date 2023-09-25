package club.mobile.d21.personalassistant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.data.Task

class TaskAdapter(private val tasks: List<Task>,
    private val onDeleteClick:(Task)-> Unit,
    private val onEditClick:(Task)->Unit,
    private val onDoneClick:(Task)->Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.task)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val deadline: TextView = itemView.findViewById(R.id.deadline)
        val detail: TextView = itemView.findViewById(R.id.detail)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val doneButton: ImageView = itemView.findViewById(R.id.doneButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.task.text = tasks[position].task
        when(tasks[position].priority){
            Priority.CRITICAL -> holder.priority.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            Priority.IMPORTANT -> holder.priority.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            Priority.LOW_PRIORITY -> holder.priority.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
        holder.priority.text = tasks[position].priority.toString()
        holder.deadline.text = String.format("%s %s", tasks[position].deadlineTime, tasks[position].deadlineDay)
        holder.detail.text = tasks[position].detail
        holder.deleteButton.setOnClickListener {
            onDeleteClick(tasks[position])
        }
        holder.editButton.setOnClickListener {
            onEditClick(tasks[position])
        }
        holder.doneButton.setOnClickListener {
            onDoneClick(tasks[position])
        }
    }
    override fun getItemCount(): Int {
        return tasks.size
    }
}
