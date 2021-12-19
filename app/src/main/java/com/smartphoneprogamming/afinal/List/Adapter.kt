package com.smartphoneprogamming.afinal.List

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartphoneprogamming.afinal.R

class Adapter(private val writinglist : ArrayList<Writing>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View?
        return when (viewType) {
            Writing.MINE -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
//            TextTypeViewHolder(view)
                return ViewHolder(view)

            }
            Writing.YOUR -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_nick, parent, false)
//            ImageTypeViewHolder(view)
                return ViewHolder(view)

            }
//            Writing.HOTEL -> {
//                view = LayoutInflater.from(parent.context).inflate(R.layout.simple_item, parent, false)
////            ImageTypeViewHolder(view)
//                return ViewHolder(view)
//
//            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val writing : Writing = writinglist[position]
        holder.question.text = writing.question
        holder.text.text = writing.text
//        holder.sub.text = writing.nick
        holder.itemView.tag = position
        holder.itemView.setOnClickListener { v ->
            val question: String = holder.question.getText().toString()
            val text: String = holder.text.getText().toString()
//            val sub: String = holder.sub.getText().toString()
            val context = v.context
            val intent: Intent
            intent = Intent(v.context, DetailActivity::class.java)
            intent.putExtra("question", question)
            intent.putExtra("text", text)
//            intent.putExtra("sub", sub)
//            intent.putExtra("photo", datalist!![position].img)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int{
        return writinglist.size
    }
    public class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val question : TextView = itemView.findViewById(R.id.question)
        val text : TextView = itemView.findViewById(R.id.text)
    }
}