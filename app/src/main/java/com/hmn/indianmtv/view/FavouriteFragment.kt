package com.hmn.indianmtv.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmn.indianmtv.R
import com.hmn.indianmtv.adapter.FavouriteAdp
import com.hmn.indianmtv.adapter.TestListener
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.room.MDatabase
import com.hmn.indianmtv.util.Constant

class FavouriteFragment : Fragment(),TestListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.back_toolbar)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.title = "Favourite Songs"
        toolbar.setNavigationOnClickListener {
            val activity = activity as MainActivity
            activity.onSupportNavigateUp()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.favourite_recycler)
        val list = MDatabase.getDatabase(requireContext()).bannerDao().getFv(true)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter = FavouriteAdp(requireContext(),list,this)
    }

    override fun onItemClick(list: BannerEntity) {
        val url = list.id
        val title = list.title
        val categoryTitle = list.category
        replaceMotionFragment(VideoViewFragment(), url, title, categoryTitle)
    }

    fun replaceMotionFragment(
        fragment: Fragment,
        url: String,
        title: String,
        categoryTitle: String
    ) {
        val bundle = Bundle()
        bundle.putString(Constant.URL, url)
        bundle.putString(Constant.TITLE, title)
        bundle.putString(Constant.CATEGORY_TITLE, categoryTitle)
        fragment.arguments = bundle
        val fr = fragmentManager?.beginTransaction()

        //fr!!.replace(R.id.callback_container, fragment).commit() // .addToBackStack(null)
        fr!!.replace(R.id.fav_container, fragment).commit()

    }
}