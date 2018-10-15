package com.zerofate.template.animation

import androidx.databinding.DataBindingUtil
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.zerofate.androidsdk.util.ToastX
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_test_fragment_transition.*

class FragmentAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragment_transition)
        val fragment = SimpleFragment()
        btn_add_fragment.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            transaction.add(R.id.fragment_container, fragment)
            transaction.commit()
        }
        btn_remove_fragment.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            transaction.remove(fragment)
            transaction.commit()
        }
    }

    class SimpleFragment : Fragment() {
        @Nullable
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            return inflater.inflate(R.layout.activity_test_fragment_transition, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val button = view.findViewById<Button>(R.id.btn_add_fragment)
            button.setOnClickListener { ToastX.showShort(activity, "hahaha") }
        }
    }


}
