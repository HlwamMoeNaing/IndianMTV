package com.hmn.indianmtv.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.hmn.indianmtv.room.BannerDao
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.room.MDatabase

class RepositoryImpl(application: Application):Repository{
    private var datab = MDatabase.getDatabase(application)


    private val bannerDao = datab.bannerDao()


    private val bannerVideo = bannerDao.getAllBanner()






    override fun getAllBanner(): List<BannerEntity> {
            return bannerVideo
    }

    override fun insertAllBanner(bannerEntity: List<BannerEntity>) {
       InsertAllBanner(bannerDao).execute(bannerEntity)
    }

    override fun deleteBanner(bannerEntity: BannerEntity) {
       DeleteBanner(bannerDao).execute(bannerEntity)
    }

    override fun deleteAllBanner() {
        DeleteAllBanner(bannerDao).execute()
    }

    override fun getDataFromCategory(category: String): LiveData<List<BannerEntity>> {
        return bannerDao.getDataFromCategory(category)
    }


    class InsertAllBanner(dao: BannerDao) : AsyncTask<List<BannerEntity>, Void, Void>() {
        private val videoDao: BannerDao = dao

        override fun doInBackground(vararg p0: List<BannerEntity>?): Void?
        {
            videoDao.insertBannerAll(p0[0]!!)
            return null
        }
    }

    class DeleteBanner(dao: BannerDao) : AsyncTask<BannerEntity, Void, Void>() {
        private val videoDao: BannerDao = dao
        override fun doInBackground(vararg p0: BannerEntity?): Void? {
            videoDao.deleteBanner(p0[0]!!)
            return null
        }

    }

    private class DeleteAllBanner(dao: BannerDao) : AsyncTask<Void, Void, Void>() {
        private val videoDao: BannerDao = dao
        override fun doInBackground(vararg params: Void?): Void? {
            videoDao.deleteAllBanner()
            return null
        }
    }

}