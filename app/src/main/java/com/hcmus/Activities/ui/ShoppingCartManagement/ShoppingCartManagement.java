package com.hcmus.Activities.ui.ShoppingCartManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ItemManagement.Bitmap_Image;
import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Const.BillDetailCustomAdapter;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShoppingCartManagement extends AppCompatActivity {

    ListView listSelectedItem;
    TextView total;
    TextView textCartItemCount;
    Button btnBuy;
    long totalPrice;
    BillDetailCustomAdapter ca;
    private Context context;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_management);

        createListItem();

        context=getApplicationContext();
        total=(TextView)findViewById(R.id.totalPrice);
        listSelectedItem=(ListView)findViewById(R.id.list_selected_item);
        progress=(ProgressBar)findViewById(R.id.load_progress);


        btnBuy=(Button)findViewById(R.id.button_buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartDomain.ListItemInCart.size() > 0) {
                    BillDto bill = new BillDto();
                    int bill_id = BillDao.Insert(bill);
                    for (int i = 0; i < CartDomain.ListItemInCart.size(); i++) {
                        BillDetailDto bill_detail = CartDomain.ListItemInCart.get(i);
                        bill_detail.setBillId(bill_id);
                        BillDetailDao.Insert(bill_detail);
                    }
                    bill = BillDao.findById(bill_id);
                    //inform bill
                    bill.setTotalPrice(totalPrice);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String currentDateTime = simpleDateFormat.format(new Date());
                    bill.setCreatedDate(currentDateTime);
                    bill.setStatus('N');
                    bill.setCustomerId(Login.userLocalStore.GetUserId());
                    bill.setShipCharge(15);
                    bill.setCompleted(false);
                    CartDomain.ListItemInCart.clear();
                    ca.notifyDataSetChanged();
                    BillDao.Update(bill);
                    total.setText("Total: ");
                }
            }
        });
    }

    public void createListItem(){
        showProgress();
        createListItemObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createListItemObserver());
    }

    private Observable<Boolean> createListItemObservable(){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    ca=new BillDetailCustomAdapter(context,CartDomain.ListItemInCart);
                    listSelectedItem.setAdapter(ca);
                } catch (Exception e){
                    Log.e("Task Create", "Error");
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
    private Observer<Boolean> createListItemObserver(){
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(final Boolean checks) {
                for (int i = 0; i < CartDomain.ListItemInCart.size(); i++) {
                    BillDetailDto bill_detail = CartDomain.ListItemInCart.get(i);
                    totalPrice += bill_detail.getAmount();
                }
                total.setText("Total: "+Long.toString(totalPrice)+" VND");
                dismissProgress();
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
    private void showProgress(){
        if (progress != null)
            progress.setVisibility(View.VISIBLE);
    }

    private void dismissProgress(){
        if (progress != null)
            progress.setVisibility(View.GONE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_shopping_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }
        return false;
    }
}
