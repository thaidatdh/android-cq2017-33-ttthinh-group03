package com.hcmus.Activities.ui.CustomerManagement;

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

public class CustomerManagementFragment extends Fragment {

    private CustomerManagementViewModel customerManagementViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerManagementViewModel =
                ViewModelProviders.of(this).get(CustomerManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_customer_management, container, false);
        final TextView textView = root.findViewById(R.id.text_customer_management);
        customerManagementViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}