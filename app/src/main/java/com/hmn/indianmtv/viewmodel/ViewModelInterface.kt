package com.hmn.indianmtv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.indianmtv.room.BannerEntity

interface ViewModelInterface {
    fun getAllCategoryOnDatabase(category: String): LiveData<List<BannerEntity>>

    fun getAllBanner(): List<BannerEntity>

//    fun getBannerFromRetrofit(): LiveData<List<BannerEntity>>

    fun insertBannerVideoAll(entity: List<BannerEntity>)

    fun deleteBannerVideo(entity: BannerEntity)

    fun deleteAllBannerVideo()

}