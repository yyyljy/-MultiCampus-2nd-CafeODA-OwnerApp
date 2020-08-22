
package com.example.ownercafeoda.MenuAdd;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ownercafeoda.OnItemClick;
import com.example.ownercafeoda.OwnerMain.OwnerMainPageActivity;
import com.example.ownercafeoda.R;

import java.util.List;


public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    private Context context;
    int row_res_id;
    List<MenuVO> data;
    private OnItemClick mCallback;

    public MenuListAdapter(Context context, int row_res_id, List<MenuVO> data,OnItemClick mCallback) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 밑에 weight가 안먹어서.. parent 와 false 추가 해줬습니다. //
        View view = LayoutInflater.from(context).inflate(row_res_id,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView row_text_view_menuname= holder.menuName;
        TextView row_text_view_menucountry= holder.menuCountry;
        TextView row_text_view_menuprice= holder.menuPrice;
        TextView row_text_view_menunum = holder.menuNumber;


        row_text_view_menuname.setText(data.get(position).getProname());
        row_text_view_menucountry.setText(data.get(position).getCountry());
        row_text_view_menuprice.setText(data.get(position).getPrice()+"");
        row_text_view_menunum.setText(data.get(position).getMenunum()+"");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuName; //layoutinflater에 있는 것
        TextView menuCountry;
        TextView menuPrice;
        TextView menuNumber;
        Button deleteMenuBtndelete;




        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.Txt_menuName);
            menuCountry = itemView.findViewById(R.id.Txt_country);
            menuPrice =  itemView.findViewById(R.id.Txt_price);
            deleteMenuBtndelete = itemView.findViewById(R.id.deleteMenuBtn);
            menuNumber = itemView.findViewById(R.id.Txt_menuNum);
            deleteMenuBtndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("---","Test Interface Onclick");
                    String dtname = menuName.getText().toString();
                    String dtcon = menuCountry.getText().toString();
                    String dtnum = menuNumber.getText().toString();
                    Bundle data = new Bundle();
                    data.putString("dtname",dtname);
                    data.putString("dtcon",dtcon);
                    data.putString("dtnum",dtnum);
                    mCallback.onClick("delete",data);

                }
            });


        }
    }




}
