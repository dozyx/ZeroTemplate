package com.zerofate.template.animation

import androidx.databinding.DataBindingUtil
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.zerofate.androidsdk.util.ToastX
import com.zerofate.template.R
import com.zerofate.template.databinding.ActivityTestFragmentTransitionBinding

class FragmentAnimationActivity : AppCompatActivity() {
    private var binding: ActivityTestFragmentTransitionBinding? = null

    protected override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_fragment_transition)
        val fragment = SimpleFragment()
        binding!!.btnAddFragment.setOnClickListener { v ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            transaction.add(R.id.fragment_container, fragment)
            transaction.commit()
        }
        binding!!.btnRemoveFragment.setOnClickListener { v ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            transaction.remove(fragment)
            transaction.commit()
        }
    }

    class SimpleFragment : Fragment() {
        @Nullable
        override fun onCreateView(
            @NonNull inflater: LayoutInflater?, @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
        ): View {

            return inflater!!.inflate(R.layout.activity_test_fragment_transition, container, false)
        }

        override fun onViewCreated(@NonNull view: View?, @Nullable savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val button = view!!.findViewById<Button>(R.id.btn_add_fragment)
            button.setOnClickListener { v -> ToastX.showShort(activity, "hahaha") }
        }
    }


}
