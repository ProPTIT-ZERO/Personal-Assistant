package club.mobile.d21.personalassistant.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Alarm
import com.google.android.material.switchmaterial.SwitchMaterial

class AlarmAdapter(private val alarms: List<Alarm>,
    private val onDeleteClick:(Alarm) -> Unit,
    private val onTurnOnClick:(Alarm) -> Unit,
    private val onTurnOffClick:(Alarm) -> Unit): RecyclerView.Adapter<AlarmAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val time: TextView = itemView.findViewById(R.id.time)
        val statusButton: ImageView = itemView.findViewById(R.id.statusButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_alarm,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = alarms[position].name
        holder.time.text = alarms[position].time
        if(alarms[position].status){
            holder.statusButton.setImageResource(R.drawable.ic_alarm_on)
        }else{
            holder.statusButton.setImageResource(R.drawable.ic_alarm_off)
        }
        holder.statusButton.setOnClickListener {
            if(alarms[position].status){
                onTurnOffClick(alarms[position])
            }else{
                onTurnOnClick(alarms[position])
            }
        }
        holder.deleteButton.setOnClickListener {
            onDeleteClick(alarms[position])
        }
    }
    override fun getItemCount(): Int {
        return alarms.size
    }
}