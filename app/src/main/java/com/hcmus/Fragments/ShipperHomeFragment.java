package com.hcmus.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.UserDto;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ShipperHomeFragment extends Fragment {
    private String username;
    private UserDto user;
    private Context mContext;
    public ShipperHomeFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shipper_home, container, false);
        TextView infoName = (TextView)root.findViewById(R.id.shipper_info_name);
        TextView address = (TextView)root.findViewById(R.id.shipper_info_address);
        TextView birthDate = (TextView)root.findViewById(R.id.shipper_info_birthdate);
        TextView phone = (TextView)root.findViewById(R.id.shipper_info_phone);
        username = Login.userLocalStore.GetUsername();
        user = UserDao.findByUsername(username);
        infoName.setText(user.getFirstName() + " " + user.getLastName());
        birthDate.setText(user.getBirthDate());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        TextView singout = (TextView)root.findViewById(R.id.txtShipperSignOut);

        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.Logout();
                Log.d(TAG, "Now log out and start the activity login");
                startActivity(new Intent(mContext, Login.class));
            }
        });
        return root;
    }
}
