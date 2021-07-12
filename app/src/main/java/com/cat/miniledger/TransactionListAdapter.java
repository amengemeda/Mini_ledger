package com.cat.miniledger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<TransactionHandler> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;



    public TransactionListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TransactionHandler> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the transaction's information
        int id= getItem(position).getTransactionId();
        String date = getItem(position).getTransactionDate();
        Double amount = getItem(position).getTransactionAmount();
        String type = getItem(position).getTransactionType();
        String source = getItem(position).getTransactionSource();
//        Log.d("Date",date);
//        Log.d("amount",amount.toString());
//        Log.d("type",type);
//        Log.d("source",source);
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        //Create the person object with the information
        TransactionHandler transactionHandler = new TransactionHandler(id,amount,date,source,type);
        TextView transactionDate = convertView.findViewById(R.id.transaction_date);
        TextView transactionType = convertView.findViewById(R.id.transaction_type);
        TextView transactionAmount = convertView.findViewById(R.id.transaction_amount);
        TextView transactionSource  = convertView.findViewById(R.id.transaction_source);

        transactionDate.setText(date);
        transactionAmount.setText(amount.toString() + " ksh");
        transactionType.setText("#"+type);
        transactionSource.setText(source);

        return convertView;
    }
}
