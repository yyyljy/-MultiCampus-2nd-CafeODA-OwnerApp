
package com.example.ownercafeoda.OrderList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ownercafeoda.FCM.FCMMain;
import com.example.ownercafeoda.R;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    Context context;
    int row_res_id;
    List<OrderListItemDTO> data;
    getCafeInfo cafeInfo;

    public OrderListAdapter(Context context, int row_res_id, List<OrderListItemDTO> data, getCafeInfo cafeInfo) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
        this.cafeInfo = cafeInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(row_res_id,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView row_text_view_guestphone= holder.guestphone;
        TextView row_text_view_ordertime = holder.ordertime;
        TextView row_text_view_menuelement= holder.menuelement;
        TextView row_text_view_icehot = holder.icehot;
        TextView row_text_view_quantity = holder.quantity;
        TextView row_text_view_price= holder.price;
        TextView row_text_view_statusmsg = holder.statusmsg;
        TextView row_text_view_ordid = holder.ordid;




        row_text_view_guestphone.setText(data.get(position).getGuestphone());
        row_text_view_ordertime.setText(data.get(position).getOrdertime());
        row_text_view_menuelement.setText(data.get(position).getMenuElement());
        row_text_view_icehot.setText(data.get(position).getIcehot());
        row_text_view_quantity.setText(data.get(position).getQuantity());
        row_text_view_price.setText(data.get(position).getPrice());
        row_text_view_statusmsg.setText(data.get(position).getStatusmsg());
        row_text_view_ordid.setText(data.get(position).getStatusmsg());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView guestphone;
        TextView ordertime;
        TextView menuelement;
        TextView icehot;
        TextView quantity;
        TextView price;
        TextView statusmsg;
        TextView ordid;
        Button updateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            guestphone = itemView.findViewById(R.id.guestphoneElement);
            ordertime =  itemView.findViewById(R.id.orderTimeElement);
            menuelement =  itemView.findViewById(R.id.menuElement);
            icehot = itemView.findViewById(R.id.icehotElement);
            quantity = itemView.findViewById(R.id.quantityElement);
            price = itemView.findViewById(R.id.priceElement);
            statusmsg = itemView.findViewById(R.id.statusmsgElement);
            ordid = itemView.findViewById(R.id.ordidElement);
            updateBtn = itemView.findViewById(R.id.pickupbtn);

        /*    updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("===","Clicked Pickupbutton");
                    String orderid = ordid.getText().toString();
                    Bundle data = new Bundle();
                    data.putString("ordid",orderid);
                    updateStatus.onClick("update",data);
                }
            });
*/


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("OwnerAppLog", "RecyclerView.ViewHolder onClick guest: "+guestphone.getText());
                    Log.d("OwnerAppLog", "RecyclerView.ViewHolder onClick owner: "+cafeInfo.getCafeId());
                    FCMMain fcmMain = new FCMMain();
                    fcmMain.request(cafeInfo.getCafeId(),guestphone.getText()+"");
                }
            });
        }
    }
}
