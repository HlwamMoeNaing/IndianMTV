package com.hmn.indianmtv.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmn.indianmtv.R
import com.hmn.indianmtv.adapter.CategoryAdapter
import com.hmn.indianmtv.adapter.RecyclerViewClickInterface
import com.hmn.indianmtv.adapter.TestListener
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.util.Constant
import com.hmn.indianmtv.viewmodel.ViewModelImplement

class DetailFragment : Fragment(), RecyclerViewClickInterface, TestListener {

    private lateinit var mViewModel: ViewModelImplement
    private lateinit var categoryTitle: String
    lateinit var recycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        recycler = view.findViewById(R.id.rv_detail_fragment)

        val toolbar = view.findViewById<Toolbar>(R.id.detail_back_toolbar)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.title = "All"
        toolbar.setNavigationOnClickListener {
            val activity = activity as MainActivity
            activity.onSupportNavigateUp()
        }
//        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.back_toolbar)
//
//        val bundle = arguments
//        categoryTitle = bundle?.getString(Constant.CATEGORY_TITLE)!!
//        toolbar.title = categoryTitle
//
//        val act =activity as MainActivity
//        act.setSupportActionBar(toolbar)

//        toolbar.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            startActivity(intent)
//            finish()
//        }

//        toolbar.setNavigationOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            startActivity(intent)
//            finish()
//        }

        init()

        return view
    }

    fun init() {

        val bundle = arguments
        categoryTitle = bundle?.getString(Constant.CATEGORY_TITLE)!!
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

        if (categoryTitle == "Top Videos") {
            recycler.adapter = CategoryAdapter(requireContext(), topList, topList.size, this, this)
        }

        if (categoryTitle == "Popular Videos") {
            recycler.adapter =
                CategoryAdapter(requireContext(), popularList, popularList.size, this, this)
        }
        if (categoryTitle == "Latest Videos") {
            recycler.adapter =
                CategoryAdapter(requireContext(), latestList, latestList.size, this, this)
        }


        val numOfColumn = resources.getInteger(R.integer.gallery_columns)
        recycler.layoutManager = GridLayoutManager(requireContext(), 3)


    }

    override fun onDetailClick(position: Int, bannerEntity: BannerEntity) {

    }

    override fun onItemCategoryClick(position: Int, bannerEntity: BannerEntity) {

    }

    override fun onItemClick(list: BannerEntity) {
        Toast.makeText(getActivity(), "Got: " + list.title, Toast.LENGTH_SHORT).show()
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
        fr!!.replace(R.id.detail_callback_container, fragment).commit() // .addToBackStack(null)


    }


}