package com.hmn.indianmtv.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmn.indianmtv.R
import com.hmn.indianmtv.adapter.MultiRecyclerAdapter
import com.hmn.indianmtv.adapter.RecyclerViewClickInterface
import com.hmn.indianmtv.adapter.TestListener
import com.hmn.indianmtv.model.Category
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.util.Constant
import com.hmn.indianmtv.viewmodel.ViewModelImplement
import java.util.*


class HomeFragment : Fragment(), RecyclerViewClickInterface, TestListener {
    private lateinit var mViewModel: ViewModelImplement
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MultiRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_home)
        toolbar.title = "Home"

        val menuImg = view.findViewById<ImageView>(R.id.menu_open)
        menuImg.setOnClickListener {
            (activity as MainActivity).openCloseNavigationDrawer()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.fragment_main_recycler)
        start()

    }

    private fun start() {
        mViewModel = ViewModelProvider(this)[ViewModelImplement::class.java]

        val entity = mViewModel.repository.getAllBanner()
        val bannerList = entity.toMutableList()
        val topList = entity.toMutableList()
        val popularList = entity.toMutableList()
        val latestList = entity.toMutableList()

        bannerList.removeIf { s -> !s.category.contains("Banner") }
        topList.removeIf { s -> !s.category.contains("Top Videos") }
        popularList.removeIf { s -> !s.category.contains("Popular Videos") }
        latestList.removeIf { s -> !s.category.contains("Latest Videos") }


        val catList = ArrayList<Category>()
        catList.add(Category(Constant.BAN, "Banner Videos", bannerList))
        catList.add(Category(Constant.TOP, "Top Videos", topList))
        catList.add(Category(Constant.POP, "Popular Videos", popularList))
        catList.add(Category(Constant.LAT, "Latest Videos", latestList))

        Log.d("@test2", catList.toString())

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        adapter = MultiRecyclerAdapter(requireContext(), catList, this, this)
        recycler.adapter = adapter

    }

    override fun onDetailClick(position: Int, bannerEntity: BannerEntity) {
        val catTitle = bannerEntity.category
        val bundle = Bundle()
        val fragment = DetailFragment()
        bundle.putString(Constant.CATEGORY_TITLE, catTitle)
        fragment.arguments = bundle
        val fr = fragmentManager?.beginTransaction()
        //fr!!.replace(R.id.fragment_container, fragment).commit()
        fr!!.replace(R.id.home_callback_container, fragment).commit()


    }

    override fun onItemCategoryClick(position: Int, bannerEntity: BannerEntity) {

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
        fr!!.replace(R.id.home_callback_container, fragment).commit()

    }

    override fun onItemClick(list: BannerEntity) {
        //Toast.makeText(getActivity(), "Got: " + list.title, Toast.LENGTH_SHORT).show()
        val url = list.id
        val title = list.title
        val categoryTitle = list.category
        replaceMotionFragment(VideoViewFragment(), url, title, categoryTitle)
    }
    companion object{
        const val TAG ="Home"
    }



}