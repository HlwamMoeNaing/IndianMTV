package com.hmn.indianmtv.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmn.indianmtv.R
import com.hmn.indianmtv.adapter.CategoryAdapter
import com.hmn.indianmtv.adapter.RecyclerViewClickInterface
import com.hmn.indianmtv.adapter.TestListener
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.util.Constant
import com.hmn.indianmtv.viewmodel.ViewModelImplement
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_video_view.*

class VideoViewFragment : Fragment(), RecyclerViewClickInterface, TestListener {
    lateinit var mViewModel: ViewModelImplement

    private var title = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_video_view, container, false)
        val playBtn = view.findViewById<AppCompatImageView>(R.id.image_play)
        val clearBtn = view.findViewById<AppCompatImageView>(R.id.image_pause)


        var isPlaying: Boolean = false
        val player = view.findViewById<YouTubePlayerView>(R.id.main_imgage_view)

        val bundle = arguments

        clearBtn.setOnClickListener {
            fragmentManager!!.beginTransaction().remove(this).commit()
        }





        lifecycle.addObserver(player)
        player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                playBtn.setOnClickListener({ view ->
                    if (isPlaying) youTubePlayer.pause() else youTubePlayer.play()
                    isPlaying = !isPlaying
                })


            }

        })


//
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ViewModelImplement::class.java)
        getRelatedList()



        videoMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                (activity as MainActivity).also {
                    //it.mainMotion.progress = abs(p3)
//                    replaceMotionFragment(it.fragment_container,this)

                }


            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

        })
        videoMotionLayout.transitionToEnd()
    }


    fun getRelatedList() {
        val entity = mViewModel.repository.getAllBanner()

        val bundle = arguments
        val categoryTitle = bundle?.getString(Constant.CATEGORY_TITLE)
        val title = bundle?.getString(Constant.TITLE)
        val url = bundle?.getString(Constant.URL)

        tv_title.text = title
        val relatedList = entity.toMutableList()
        relatedList.removeIf { s -> !s.category.contains(categoryTitle!!) }
        relatedList.removeIf { s -> s.id.contains(url!!) }
        val categoryAdapter =
            CategoryAdapter(requireContext(), relatedList, relatedList.size, this, this, true)
        //val categoryAdapter = MultiRecyclerAdapter(requireContext(),)
        m_recycler_View.adapter = categoryAdapter
        m_recycler_View.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onDetailClick(position: Int, bannerEntity: BannerEntity) {
        Toast.makeText(getActivity(), "Got: " + bannerEntity.title, Toast.LENGTH_SHORT).show()
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
        fr!!.replace(R.id.self, fragment).commit() // .addToBackStack(null)


    }

    override fun onItemClick(list: BannerEntity) {
        Toast.makeText(getActivity(), "Got: " + list.title, Toast.LENGTH_SHORT).show()
        val url = list.id
        val title = list.title
        val categoryTitle = list.category
        replaceMotionFragment(VideoViewFragment(), url, title, categoryTitle)
    }
}