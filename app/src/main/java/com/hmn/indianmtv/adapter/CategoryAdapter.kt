package com.hmn.indianmtv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.hmn.indianmtv.R
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.room.MDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CategoryAdapter(
    val context: Context,
    val list: MutableList<BannerEntity>,
    val size: Int,
    val callbacks: RecyclerViewClickInterface,
    val listener: TestListener,
    val rel: Boolean = false

) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val categoryImage: AppCompatImageView = view.findViewById(R.id.image) ?: view.findViewById(R.id.poster)
        val favImage = view.findViewById<AppCompatImageView>(R.id.img_add_fav) ?: view.findViewById<CircleImageView>(R.id.example_poster)


        val poster = view.findViewById<ImageView>(R.id.poster)
        val thumbPoster = view.findViewById<CircleImageView>(R.id.example_poster)
        //val title = view.findViewById<TextView>(R.id.tv_title)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (rel == false ){
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
            )
        }else{
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.related_items, parent, false))
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val url = "https://img.youtube.com/vi/" + list[position].id + "/mqdefault.jpg"
        if (rel == false){
            val bannerEntity = list[position]

            Picasso.get().load(url).into(holder.categoryImage)

            if (bannerEntity.isFav) {
                holder.favImage.setImageResource(R.drawable.favourite_full)
            } else {
                holder.favImage.setImageResource(R.drawable.favourite_boarder)
            }

            holder.itemView.setOnClickListener {
                callbacks.onDetailClick(position, list[position])
                listener.onItemClick(list[position])
            }



            holder.favImage.setOnClickListener {
                bannerEntity.isFav = !bannerEntity.isFav
                list[position] = bannerEntity
                MDatabase.getDatabase(context).bannerDao().updateBanner(bannerEntity)
                notifyItemChanged(position, bannerEntity)
            }


        }else{
            val title = list[position].title
            Picasso.get().load(url).into(holder.poster)
            Picasso.get().load(url).into(holder.thumbPoster)

            holder.itemView.setOnClickListener {
                listener.onItemClick(list[position])
            }
          //  holder.title.text = title

        }





//        if (holder is MultiRecyclerAdapter.RelatedViewHolder){
           // val url = "https://img.youtube.com/vi/" + list[position].vlist[position].id + "/mqdefault.jpg"

//        }

    }

    override fun getItemCount(): Int {
        return size
    }





}