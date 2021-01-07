package com.hmn.indianmtv.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hmn.indianmtv.R
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.util.Constant

class FavouriteAdp(val context: Context, val list: List<BannerEntity>,val listener: TestListener) :
    RecyclerView.Adapter<FavouriteAdp.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: AppCompatImageView = view.findViewById(R.id.video_player)
        val title: TextView = view.findViewById(R.id.video_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title

        val url = "https://img.youtube.com/vi/" + list[position].id + "/mqdefault.jpg"

        Glide.with(context).asBitmap().load(url).apply(RequestOptions().override(180, 100))
            .into(holder.videoView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(list[position])


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}