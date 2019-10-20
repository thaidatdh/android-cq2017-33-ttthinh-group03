
package com.hcmus.Activities.ui.BillManagement;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;

import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.ConversionUtils;
import com.hcmus.shipe.R;

import java.util.List;

public class BillManagementFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1 ;
    private List<BillDto> listBill;
    private ListView billListView;

    CustomListAdapter adapter;
    Dialog myDialog;
    List<BillDetailDto> billDetailList;
    private ListView listBillDetail;
    BillListAdapter billAdapter;
    private BillDto selectedBill;
    private int selectedType = 0;
    private boolean recordChanged = false;
    private final String[] BILL_TYPE = {"New", "Getting", "On-Going", "Completed", "All"};
    public static final String[] BILL_ALL_STATUS = {"New", "Getting", "On-Going", "Completed"};
    private final String[] BILL_STATUS = {"Active", "Inactive"};
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_PHONE_CALL = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  billManagementViewModel =
        //        ViewModelProviders.of(this).get(BillManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bill_management, container, false);
        Spinner billTypeSpinner = root.findViewById(R.id.bill_type_spinner);
        billTypeSpinner.setAdapter(new ArrayAdapter<String>(root.getContext(), R.layout.support_simple_spinner_dropdown_item, BILL_TYPE));
        billTypeSpinner.setSelection(4);
        billListView = root.findViewById(R.id.list_bill_view);
        billTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = i;
                SetListView(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBill = listBill.get(position);
                myDialog = new Dialog(view.getContext());
                recordChanged = false;
                ShowPopup();
            }
        });
        return root;
    }


    private void SetListView(int typeIndex) {
        switch (typeIndex) {
            case 0:
                listBill = BillDao.GetNew();
                SetListViewBill();
                break;
            case 1:
                listBill = BillDao.GetGetting();
                SetListViewBill();
                break;
            case 2:
                listBill = BillDao.GetOnGoing();
                SetListViewBill();
                break;
            case 3:
                listBill = BillDao.GetCompleted();
                SetListViewBill();
                break;
            default:
                listBill = BillDao.SelectAll();
                SetListViewBill();
                break;
        }
    }

    private void ShowPopup(){
        Dialog view = myDialog;
        view.setCancelable(true);
        view.setContentView(R.layout.bill_info_popup);
        TextView customer = (TextView)view.findViewById(R.id.bill_info_customer);
        final TextView phone = (TextView)view.findViewById(R.id.bill_phone_customer);
        TextView shipper= (TextView)view.findViewById(R.id.bill_info_shipper);
        TextView description = (TextView)view.findViewById(R.id.bill_info_description);
        TextView created = (TextView)view.findViewById(R.id.bill_info_created);
        TextView delivery = (TextView)view.findViewById(R.id.bill_info_delivery);
        TextView statusSpinner = (TextView)view.findViewById(R.id.bill_info_status);
        ImageView closeBtn = (ImageView)view.findViewById(R.id.bill_info_close);
        if (selectedBill!=null) {
            UserDto billCustomer = UserDao.findById(selectedBill.getCustomerId());
            UserDto billShipper = UserDao.findById(selectedBill.getShipperId());
            String namecustomer = "";
            String nameshipper = "";
            if (billCustomer != null) {
                String fcustomer = "", lcustomer = "";
                if (billCustomer.getLastName() != null){
                    lcustomer = billCustomer.getLastName().trim();
                }
                if (billCustomer.getFirstName() != null){
                    fcustomer = billCustomer.getFirstName().trim();
                }
                namecustomer = lcustomer + " " + fcustomer;
            }
            if (billShipper != null) {
                String fshipper = "", lshipper = "";
                if (billShipper.getLastName() != null){
                    lshipper = billShipper.getLastName().trim();
                }
                if (billShipper.getFirstName() != null){
                    fshipper = billShipper.getFirstName().trim();
                }
                nameshipper = lshipper + " " + fshipper;
            }
            customer.setText(namecustomer);
            phone.setText(billCustomer.getPhone());
            phone.setTextColor(Color.parseColor("#00ccff"));
            shipper.setText(nameshipper);
            description.setText(selectedBill.getDescription());
            created.setText(ConversionUtils.DateTime.formatDate(selectedBill.getCreatedDate()));
            delivery.setText(ConversionUtils.DateTime.formatDate(selectedBill.getDeliverTime()));
            switch (selectedBill.getStatus()) {
                case 'N':
                    statusSpinner.setText(R.string.N);
                    statusSpinner.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case 'G':
                    statusSpinner.setText(R.string.G);
                    statusSpinner.setTextColor(Color.parseColor("#0000ff"));
                    break;
                case 'O':
                    statusSpinner.setText(R.string.O);
                    statusSpinner.setTextColor(Color.parseColor("#00ff00"));
                    break;
                case 'C':
                    statusSpinner.setText(R.string.C);
                    statusSpinner.setTextColor(Color.parseColor("#000000"));
                    break;
            }

            billDetailList = BillDetailDao.findById(selectedBill.getBillId());
            listBillDetail = (ListView)view.findViewById(R.id.listBillDetail);
            billAdapter = new BillListAdapter(getContext(), R.layout.bill_detail_list_item , billDetailList);
            listBillDetail.setAdapter(billAdapter);
        }
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numPhone = phone.getText().toString();
                makePhoneCall(numPhone);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (recordChanged) {
                    SetListView(selectedType);
                }
            }
        });
        myDialog.show();
    }

    private void makePhoneCall(String numPhone) {

        numPhone = numPhone.replace("-", "");
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
                requestPermission();
            }
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numPhone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED ;

    }

    private void requestPermission() {

        requestPermissions( new String[]
                {
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE
                }, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                TextView phone = (TextView)getView().findViewById(R.id.bill_phone_customer);

                if (grantResults.length > 0) {

                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (CallPermission) {

                        Toast.makeText(getActivity(),
                                "Permission accepted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "Permission denied", Toast.LENGTH_LONG).show();
                     phone.setEnabled(false);

                    }
                    break;
                }
        }
    }


    private void SetListViewBill() {
        adapter = new CustomListAdapter(getContext(), R.layout.bill_list_item, listBill);
        billListView.setAdapter(adapter);
    }

    public static class CustomListAdapter extends ArrayAdapter<BillDto> {
        Context myContext;
        int myLayout;
        List<BillDto> myList;

        public CustomListAdapter(Context context, int layout, List<BillDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }

        @Override
        public int getCount() {
            if (myList != null)
                return myList.size();
            else
                return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bill_list_item, null);
            //RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_Bill);
            TextView customerName = (TextView)view.findViewById(R.id.textView_Customer_Bill);
            TextView shipperName = (TextView)view.findViewById(R.id.textView_Shipper_Bill);
            TextView billPrice = (TextView)view.findViewById(R.id.textView_Price_Bill);
            TextView billStatus = (TextView)view.findViewById(R.id.textView_Status_Bill);
            BillDto billDto = myList.get(i);
            UserDto billCustomer = UserDao.findById(billDto.getCustomerId());
            UserDto billShipper = UserDao.findById(billDto.getShipperId());
            String namecustomer = "";
            String nameshipper = "";
            if (billCustomer != null) {
                String fcustomer = "", lcustomer = "";
                if (billCustomer.getLastName() != null){
                    lcustomer = billCustomer.getLastName().trim();
                }
                if (billCustomer.getFirstName() != null){
                    fcustomer = billCustomer.getFirstName().trim();
                }
                namecustomer = lcustomer + " " + fcustomer;
            }
            if (billShipper != null) {
                String fshipper = "", lshipper = "";
                if (billShipper.getLastName() != null){
                    lshipper = billShipper.getLastName().trim();
                }
                if (billShipper.getFirstName() != null){
                    fshipper = billShipper.getFirstName().trim();
                }
                nameshipper = ("Shipper: " + lshipper + " " + fshipper).trim();
            }



            customerName.setText(namecustomer);
            shipperName.setText(nameshipper);
            Long totalPrice =  billDto.getTotalPrice() + billDto.getShipCharge();
            String price = totalPrice.toString() + " VNĐ";
            billPrice.setText(price);
            switch (billDto.getStatus()) {
                case 'N':
                    billStatus.setText(R.string.N);
                    billStatus.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case 'G':
                    billStatus.setText(R.string.G);
                    billStatus.setTextColor(Color.parseColor("#0000ff"));
                    break;
                case 'O':
                    billStatus.setText(R.string.O);
                    billStatus.setTextColor(Color.parseColor("#00ff00"));
                    break;
                case 'C':
                    billStatus.setText(R.string.C);
                    billStatus.setTextColor(Color.parseColor("#000000"));
                    break;
            }
            return view;
        }
    }
    public static class BillListAdapter extends ArrayAdapter<BillDetailDto> {
        Context myContext;
        int myLayout;
        List<BillDetailDto> myList;

        public BillListAdapter(Context context, int layout, List<BillDetailDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }

        @Override
        public int getCount() {
            if (myList != null)
                return myList.size();
            else
                return 0;
        }

        /*@Override
        public Object getItem(int position) {
            return listBill.get(position);
        }
*/
        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bill_detail_list_item, null);
            TextView itemName = (TextView)view.findViewById(R.id.textView_Name_Item);
            TextView itemPrice = (TextView)view.findViewById(R.id.textView_Price_Item);
            TextView amountItem = (TextView)view.findViewById(R.id.textView_Amount);
            TextView total_price = (TextView)view.findViewById(R.id.textView_Total);
            BillDetailDto billDetail = myList.get(i);
            ItemDto item = ItemDao.findById(billDetail.getItemId());
            String name = item.getName().trim();
            Long price = item.getPrice();
            int amount = billDetail.getAmount();
            Long total = price * amount;

            itemName.setText(name);
            itemPrice.setText(price.toString());
            amountItem.setText(String.valueOf(amount));
            total_price.setText(total.toString());
            return view;
        }
    }
}
