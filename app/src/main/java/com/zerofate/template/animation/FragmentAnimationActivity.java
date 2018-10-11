package com.zerofate.template.animation;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zerofate.androidsdk.util.ToastX;
import com.zerofate.template.R;
import com.zerofate.template.databinding.ActivityTestFragmentTransitionBinding;

public class FragmentAnimationActivity extends AppCompatActivity {
    private ActivityTestFragmentTransitionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_fragment_transition);
        Fragment  fragment = new SimpleFragment();
        binding.btnAddFragment.setOnClickListener(v -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
            transaction.add(R.id.fragment_container,fragment);
            transaction.commit();
        });
        binding.btnRemoveFragment.setOnClickListener(v -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
            transaction.remove(fragment);
            transaction.commit();
        });
    }

    public static class SimpleFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

            return inflater.inflate(R.layout.activity_test_fragment_transition,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Button button = view.findViewById(R.id.btn_add_fragment);
            button.setOnClickListener(v -> {
                ToastX.showShort(getActivity(),"hahaha");
            });
        }
    }


}
