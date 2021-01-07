package com.hmn.indianmtv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.hmn.indianmtv.R
import com.hmn.indianmtv.room.BannerEntity
import com.squareup.picasso.Picasso

class BannerViewPagerAdapter(val context: Context, val list: List<BannerEntity>,val listener: TestListener)
    : PagerAdapter() {


    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("InflateParams")
    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout = inflater.inflate(R.layout.banner_image_item, null)
        val slideImg = slideLayout.findViewById<AppCompatImageView>(R.id.image_banner)
        val url =  "https://img.youtube.com/vi/" + list[position].id + "/mqdefault.jpg"
        Picasso.get().load(url).into(slideImg)



        slideLayout.setOnClickListener {
//            val category = list[position].category
//            val i = Intent(context, PlayerActivity::class.java)
//            i.putExtra(Constant.CATEGORY_TITLE, category)
//            i.putExtra(Constant.URL, list[position].id)
//            i.putExtra(Constant.TITLE, list[position].title)
//            context.startActivity(i)
//
            listener.onItemClick(list[position])
        }

        container.addView(slideLayout,0)
        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}