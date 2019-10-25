package com.hcmus.Activities.ui.ShopInformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hcmus.shipe.R;

public class ShopInformationFragment extends Fragment {

    private ShopInformationViewModel shopInformationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       shopInformationViewModel =
                ViewModelProviders.of(this).get(ShopInformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop_information, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shopInformationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
