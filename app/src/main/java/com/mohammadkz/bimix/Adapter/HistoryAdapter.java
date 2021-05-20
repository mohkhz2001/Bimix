package com.mohammadkz.bimix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammadkz.bimix.Model.InsuranceResponse;
import com.mohammadkz.bimix.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {

    private Context context;
    private List<InsuranceResponse> insuranceResponses;

    public HistoryAdapter(Context context, List<InsuranceResponse> insuranceResponses) {
        this.context = context;
        this.insuranceResponses = insuranceResponses;
    }

    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history, parent, false);
        return new HistoryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {
        holder.date_req.setText(insuranceResponses.get(position).getDate());
        holder.payCode.setText(insuranceResponses.get(position).getPayCode());

        holder.trackingCode.setText(insuranceResponses.get(position).getTrackingCode());

        if (insuranceResponses.get(position).getStatus().equals("pending")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.GONE);
            holder.payCode_txt.setVisibility(View.GONE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("در حال بررسی");
        } else if (insuranceResponses.get(position).getStatus().equals("confirm")) {
            holder.get.setVisibility(View.VISIBLE);
            holder.payCode.setVisibility(View.VISIBLE);
            holder.payCode_txt.setVisibility(View.VISIBLE);
            holder.get_txt.setVisibility(View.VISIBLE);
            holder.status.setText("صادر شده");
        } else if (insuranceResponses.get(position).getStatus().equals("rejected")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.GONE);
            holder.payCode_txt.setVisibility(View.GONE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("رد شده");
        } else if (insuranceResponses.get(position).getStatus().equals("payment")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.VISIBLE);
            holder.payCode_txt.setVisibility(View.VISIBLE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("منتظر پرداخت");
        }

    }

    @Override
    public int getItemCount() {
        return insuranceResponses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView describe, status, trackingCode, date_req, payCode;
        TextView payCode_txt, get_txt;
        Button get;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.describe = itemView.findViewById(R.id.describe);
            this.status = itemView.findViewById(R.id.status);
            this.trackingCode = itemView.findViewById(R.id.trackingCode);
            this.date_req = itemView.findViewById(R.id.date_req);
            this.payCode = itemView.findViewById(R.id.payCode);
            this.get = itemView.findViewById(R.id.get);
            this.payCode_txt = itemView.findViewById(R.id.payCode_txt);
            this.get_txt = itemView.findViewById(R.id.get_txt);

        }
    }

}
