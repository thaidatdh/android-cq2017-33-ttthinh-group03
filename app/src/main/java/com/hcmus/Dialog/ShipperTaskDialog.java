package com.hcmus.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hcmus.Activities.ui.BillManagement.BillManagementFragment;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.Models.Task;
import com.hcmus.Utils.DialogBtnCallBackInterface;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShipperTaskDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private List<Task> mOrderList;
    private int mIndex;
    private DialogBtnCallBackInterface mCallback;

    private ScrollView dialogInfo;
    private ProgressBar infoProgress;

    private Button accepBtn;
    private Button closeBtn;
    private ListView taskListItem;
    private TextView billId;
    private TextView customerName;
    private TextView customerAddress;
    private TextView customerPhone;
    private TextView totalPrice;
    private TextView description;
    private TextView deliveryDate;

    public ShipperTaskDialog(@NonNull Context context, List<Task> orders, int index, DialogBtnCallBackInterface callback){
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
        setContentView(R.layout.shipper_task_dialog);
        initView();
    }

    private void initView(){
        dialogInfo = (ScrollView) findViewById(R.id.dialog_info);
        infoProgress = (ProgressBar) findViewById(R.id.info_progress);

        accepBtn = (Button) findViewById(R.id.task_accept_btn);
        closeBtn = (Button) findViewById(R.id.task_close_btn);

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
        Task task = mOrderList.get(mIndex);

        showProgress();
        createBillDetailObservable(task.getBillId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createBillDetailObserver());

        billId.setText(String.valueOf(task.getBillId()));
        String name = task.getFirstName() + " " + task.getLastName();
        customerName.setText(name);
        customerAddress.setText(task.getAddress());
        customerPhone.setText(task.getPhone());
        totalPrice.setText(String.valueOf(task.getTotalPrice()));
        description.setText(task.getDescription());
        deliveryDate.setText(task.getDeliverTime());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnClose();
            }
        });
        accepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dismiss();
                mCallback.onBtnClick("");
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
}
