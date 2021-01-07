package com.hmn.indianmtv.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.indianmtv.room.BannerEntity

interface Repository {

//    fun getBannerModel(): MutableLiveData<List<BannerEntity>>
    fun getAllBanner():List<BannerEntity>
    fun insertAllBanner(bannerEntity: List<BannerEntity>)
    fun deleteBanner(bannerEntity: BannerEntity)
    fun deleteAllBanner()
    fun getDataFromCategory(category:String): LiveData<List<BannerEntity>>







}