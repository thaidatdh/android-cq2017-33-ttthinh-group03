package com.hcmus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hcmus.Activities.ui.BillManagement.BillManagementFragment;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.Models.Task;
import com.hcmus.Utils.ConversionUtils;
import com.hcmus.Utils.DialogBtnCallBackInterface;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.hcmus.Utils.Constant;

public class ShipperOrderDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private List<Task> mOrderList;
    private int mIndex;
    private char mStatus;
    private DialogBtnCallBackInterface mCallback;

    private ScrollView dialogInfo;
    private ProgressBar infoProgress;

    private Button cancelBtn;
    private Button closeBtn;
    private Button statusBtn;
    private ListView taskListItem;
    private TextView billId;
    private TextView customerName;
    private TextView customerAddress;
    private TextView customerPhone;
    private TextView totalPrice;
    private TextView description;
    private TextView deliveryDate;

    public ShipperOrderDialog(@NonNull Context context, List<Task> orders, int index, DialogBtnCallBackInterface callback){
        super(context);
        mContext = context;
        mOrderList = orders;
        mIndex = index;
        mCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shipper_order_dialog);
        initView();
    }

    private void initView(){
        dialogInfo = (ScrollView) findViewById(R.id.dialog_info);
        infoProgress = (ProgressBar) findViewById(R.id.info_progress);

        cancelBtn = (Button) findViewById(R.id.task_cancel_btn);
        closeBtn = (Button) findViewById(R.id.task_close_btn);
        statusBtn = (Button) findViewById(R.id.task_status_btn);

        //Value
        taskListItem = (ListView) findViewById(R.id.task_list_item);
        billId = (TextView) findViewById(R.id.task_bill_id);
        customerName = (TextView) findViewById(R.id.task_customer_name);
        customerAddress = (TextView)findViewById(R.id.task_customer_address);
        customerPhone = (TextView) findViewById(R.id.task_customer_phone);
        totalPrice = (TextView)findViewById(R.id.bill_total_price);
        description = (TextView)findViewById(R.id.task_bill_description);
        deliveryDate = (TextView)findViewById(R.id.task_delivery_date);
    }

    @Override
    public void show(){
        super.show();
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        setDialogValue();
    }

    public void setDialogValue(){
        final Task task = mOrderList.get(mIndex);

        final int id = task.getBillId();
        showProgress();
        createBillDetailObservable(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createBillDetailObserver());

        billId.setText(String.valueOf(id));
        String name = task.getFirstName() + " " + task.getLastName();
        customerName.setText(name);
        customerAddress.setText(task.getAddress());
        customerPhone.setText(task.getPhone());
        totalPrice.setText(String.valueOf(task.getTotalPrice()));
        description.setText(task.getDescription());

        Date date = ConversionUtils.DateTime.parseDate(task.getDeliverTime(), "yyyy-MM-dd HH:mm:ss.s");
        deliveryDate.setText(ConversionUtils.DateTime.formatDate(date, "HH:mm dd/MM/yyyy"));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnClose();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onBtnClick("Cancel");
            }
        });

        mStatus = task.getStatus();
        int statusIdx = Constant.billStatus.indexOf(mStatus);
        if (statusIdx < Constant.billStatus.length() - 1){
            mStatus = Constant.billStatus.charAt(statusIdx + 1);

        }
        setStatusBtn(mStatus);
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.cancel();
                            }
                        });
                switch(mStatus){
                    case 'O':
                        builder.setMessage("Are you sure you want to change this task's status to ON GOING")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, final int idd) {
                                        handleOnGoingBtnClick(id);
                                        task.setStatus(mStatus);
                                        mStatus = 'C';
                                    }
                                });

                        break;
                    case 'C':
                        builder.setMessage("Are you sure you want to change this task's status to COMPLETE")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, final int idd) {
                                        handleCompleteBtnClick(id);
                                        task.setStatus(mStatus);
                                        dismiss();
                                        mCallback.onBtnClick("Complete");
                                    }
                                });
                        break;
                }
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.task_close_btn:
                handleBtnClose();
                break;
        }
    }

    private void handleBtnClose() {
        mIndex = -1;
        dismiss();
    }

    private Observable<List<BillDetailDto>> createBillDetailObservable(final int billId){
        return Observable.fromCallable(new Callable<List<BillDetailDto>>() {
            @Override
            public List<BillDetailDto> call() throws Exception {
                List<BillDetailDto> detail = new ArrayList<>();
                try {
                    detail = BillDetailDao.findById(billId);

                } catch (Exception e){
                    Log.e("Task Create", "Error");
                    e.printStackTrace();
                }
                return detail;
            }
        });
    }
    private Observer<List<BillDetailDto>> createBillDetailObserver(){
        return new Observer<List<BillDetailDto>>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(final List<BillDetailDto> detail) {
                BillManagementFragment.BillListAdapter listItemAdapter = new BillManagementFragment.BillListAdapter(mContext, R.layout.bill_detail_list_item, detail);
                taskListItem.setAdapter(listItemAdapter);
                showInfoView();
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };
    }

    private void showProgress(){
        if (infoProgress != null)
            infoProgress.setVisibility(View.VISIBLE);
    }


    private void dismissProgress(){
        if (infoProgress != null)
            infoProgress.setVisibility(View.GONE);
    }

    private void showInfoView(){
        if (dialogInfo != null){
            dialogInfo.setVisibility(View.VISIBLE);
        }
    }
    private void handleOnGoingBtnClick(int billId) {
        BillDao.SetStatusBill(billId, 'O');
        setStatusBtn('C');
    }
    private void handleCompleteBtnClick(int billId) {
        BillDao.SetStatusBill(billId, 'C');
    }
    private void setStatusBtn(char status){
        switch(status){
            case 'O':
                statusBtn.setText(R.string.task_on_going_msg);
                statusBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.status_on_going_button_style));
                break;
            case 'C':
                statusBtn.setText(R.string.task_complete_msg);
                statusBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.status_complete_button_style));
                break;
        }
    }


}
