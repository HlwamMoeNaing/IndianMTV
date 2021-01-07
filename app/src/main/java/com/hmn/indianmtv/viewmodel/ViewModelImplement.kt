package com.hmn.indianmtv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.indianmtv.repo.RepositoryImpl
import com.hmn.indianmtv.room.BannerEntity

class ViewModelImplement(application: Application) : AndroidViewModel(application),
    ViewModelInterface {

    val repository = RepositoryImpl(application)


    private lateinit var bannerVideoList: MutableLiveData<List<BannerEntity>>

    override fun getAllCategoryOnDatabase(category: String): LiveData<List<BannerEntity>> {
        return repository.getDataFromCategory(category)
    }


    override fun getAllBanner(): List<BannerEntity> {
        return repository.getAllBanner()
    }


//    override fun getBannerFromRetrofit(): LiveData<List<BannerEntity>> {
//        bannerVideoList = repository.getBannerModel()
//        return bannerVideoList
//    }



    override fun insertBannerVideoAll(entity: List<BannerEntity>) {
        repository.insertAllBanner(entity)
    }



    override fun deleteBannerVideo(entity: BannerEntity) {
        repository.deleteBanner(entity)
    }




    override fun deleteAllBannerVideo() {
        repository.deleteAllBanner()
    }




}