package club.mobile.d21.personalassistant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Note
import java.time.format.DateTimeFormatter

class NoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val note: TextView = itemView.findViewById(R.id.description)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = notes[position].date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        holder.note.text = notes[position].content
    }
    override fun getItemCount(): Int {
        return notes.size
    }
}
