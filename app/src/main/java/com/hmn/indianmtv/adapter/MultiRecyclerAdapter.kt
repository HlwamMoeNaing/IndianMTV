package com.hmn.indianmtv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hmn.indianmtv.R
import com.hmn.indianmtv.model.Category

class MultiRecyclerAdapter(
    val context: Context,
    val list: List<Category>,
    val callback: RecyclerViewClickInterface,
    val listener: TestListener,


    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val BAN = 1
    private val CAT = 2



    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewPager: ViewPager = view.findViewById(R.id.banner_view_pager)
        val indicator: TabLayout = view.findViewById(R.id.tab_indicator)
    }

    class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catRecycler: RecyclerView = view.findViewById(R.id.category_recycler)
        val title: TextView = view.findViewById(R.id.category_title)
        val viewAll: TextView = view.findViewById(R.id.tv_view_all)

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == BAN) {
            BannerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.banner_view_pager, parent, false)
                //    LayoutInflater.from(context).inflate(R.layout.view_flipper, parent, false)
            )
        } else {
            CatViewHolder(
                LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BannerViewHolder) {
            val pager = holder.viewPager
            val indicator = holder.indicator

            pager.adapter = BannerViewPagerAdapter(context,list[position].vlist,listener)
            indicator.setupWithViewPager(pager)


        }

        if (holder is CatViewHolder) {
            holder.title.text = list[position].tit
            holder.viewAll.setOnClickListener {

                listener.onItemClick(list[position].vlist[position])


                callback.onDetailClick(position,list[position].vlist[position])


            }



            holder.catRecycler.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            holder.catRecycler.setHasFixedSize(true)
            holder.catRecycler.adapter =
                CategoryAdapter(
                    context,
                    list[position].vlist,
                    6,
                    callback,
                    listener,
                    false

                )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].typ == BAN) {
            BAN
        } else {
            CAT
        }


    }


}







