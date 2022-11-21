package com.example.bloodpressureapp.ui.main
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView


class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initBottomNav()
    }

    private fun initBottomNav(){
        val bottomNavigationMenuView =
            binding.navBottom.getChildAt(0) as BottomNavigationMenuView
        val itemView = bottomNavigationMenuView.getChildAt(1) as BottomNavigationItemView
        itemView.setIconTintList(null)
        itemView.setIconSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 40f,
                resources.displayMetrics
            ).toInt()
        )


    }

}