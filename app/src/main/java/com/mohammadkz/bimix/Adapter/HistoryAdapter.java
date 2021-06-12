package com.mohammadkz.bimix.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammadkz.bimix.Model.InsuranceResponse;
import com.mohammadkz.bimix.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {

    private Context context;
    private List<InsuranceResponse> insuranceResponses;
    public OnItemClickListener onPayItemClickListener, onPayedItemClickListener, onGetItemClickListener;

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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.date_req.setText(insuranceResponses.get(position).getDate());
        holder.payCode.setText(insuranceResponses.get(position).getPayCode());
        holder.describe.setText(insuranceResponses.get(position).getDescribe());

        holder.trackingCode.setText(insuranceResponses.get(position).getTrackingCode());

        setKind(position, holder);
        setVisibility(position, holder);

    }

    private void setKind(int pos, viewHolder holder) {
        if (insuranceResponses.get(pos).getKind().equals("body_insurance")) {
            holder.kind_txt.setText("بیمه بدنه");
        } else if (insuranceResponses.get(pos).getKind().equals("third_party")) {
            holder.kind_txt.setText("بیمه شخص ثالث");
        } else if (insuranceResponses.get(pos).getKind().equals("fire_insurance")) {
            holder.kind_txt.setText("بیمه آتش سوزی");
        }
    }

    private void setVisibility(int pos, viewHolder holder) {
        if (insuranceResponses.get(pos).getStatus().equals("pending")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.GONE);
            holder.payCode_txt.setVisibility(View.GONE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("در حال بررسی");
        } else if (insuranceResponses.get(pos).getStatus().equals("confirm")) {
            holder.get.setVisibility(View.VISIBLE);
            holder.payCode.setVisibility(View.VISIBLE);
            holder.payCode_txt.setVisibility(View.VISIBLE);
            holder.get_txt.setVisibility(View.VISIBLE);
            holder.status.setText("صادر شده");
        } else if (insuranceResponses.get(pos).getStatus().equals("rejected")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.GONE);
            holder.payCode_txt.setVisibility(View.GONE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("رد شده");
        } else if (insuranceResponses.get(pos).getStatus().equals("issuing")) {
            holder.get.setVisibility(View.GONE);
            holder.payCode.setVisibility(View.VISIBLE);
            holder.payCode_txt.setVisibility(View.VISIBLE);
            holder.get_txt.setVisibility(View.GONE);
            holder.status.setText("در حال صدور");
        } else if (insuranceResponses.get(pos).getStatus().equals("payment")) {
            try {
                if (insuranceResponses.get(pos).getPayed().equals("-")) {
                    holder.payed.setVisibility(View.VISIBLE);
                    holder.pay.setVisibility(View.VISIBLE);
                } else {
                    holder.payed_txt.setVisibility(View.VISIBLE);
                }
                holder.get.setVisibility(View.GONE);
                holder.payCode.setVisibility(View.VISIBLE);
                holder.payCode_txt.setVisibility(View.VISIBLE);
                holder.get_txt.setVisibility(View.GONE);
                holder.status.setText("منتظر پرداخت");
            } catch (Exception e) {
                Log.e("error ", " " + pos);
                e.getMessage();
            }

        }
    }

    @Override
    public int getItemCount() {
        return insuranceResponses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView describe, status, trackingCode, date_req, payCode;
        TextView payCode_txt, get_txt, kind_txt, payed_txt;
        Button get, payed, pay;

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
            this.kind_txt = itemView.findViewById(R.id.kind_txt);
            this.payed = itemView.findViewById(R.id.payed);
            this.pay = itemView.findViewById(R.id.pay);
            this.payed_txt = itemView.findViewById(R.id.payed_txt);

            payed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPayedItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });

            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGetItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPayItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });

        }
    }

    public void setOnPayedItemClickListener(OnItemClickListener onPayedItemClickListener) {
        this.onPayedItemClickListener = onPayedItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }

    public void setOnGetItemClickListener(OnItemClickListener onGetItemClickListener) {
        this.onGetItemClickListener = onGetItemClickListener;
    }

    public void setOnPayItemClickListener(OnItemClickListener onPayItemClickListener) {
        this.onPayItemClickListener = onPayItemClickListener;
    }

}
