package com.hmn.indianmtv.adapter

import com.hmn.indianmtv.room.BannerEntity

interface RecyclerViewClickInterface {
    fun onDetailClick(position:Int, bannerEntity: BannerEntity)
    fun onItemCategoryClick(position:Int,bannerEntity: BannerEntity)
}