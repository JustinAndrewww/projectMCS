package com.example.projectmcs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DollAdapter(private val dollList: List<Doll>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<DollAdapter.DollViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(item: Doll)
    }

    class DollViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.dollName)
        val imageView: ImageView = view.findViewById(R.id.dollImage)
        val cardView:CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DollViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DollViewHolder(view)
    }

    override fun onBindViewHolder(holder: DollViewHolder, position: Int) {
        val doll = dollList[position]
        holder.nameTextView.text = doll.dollName
        Picasso.get().load(doll.imageLink).into(holder.imageView)

        holder.cardView.setOnClickListener {
            itemClickListener.onItemClick(doll)
        }
    }

    override fun getItemCount() = dollList.size
}
