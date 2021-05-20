package com.mohammadkz.bimix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.mohammadkz.bimix.Model.Cover;
import com.mohammadkz.bimix.R;

import java.util.ArrayList;

public class ChooseBoxAdapter extends RecyclerView.Adapter<ChooseBoxAdapter.viewHolder> {

    private Context context;
    private ArrayList<Cover> coverList;
    private OnCheckedChangeListener onCheckedChangeListener;

    public ChooseBoxAdapter(Context context, ArrayList<Cover> coverList) {
        this.context = context;
        this.coverList = coverList;

    }


    @NonNull
    @Override
    public ChooseBoxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_box_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseBoxAdapter.viewHolder holder, int position) {
        holder.txt.setText(coverList.get(position).getText());
        holder.describe.setText(coverList.get(position).getDescribe());

        boolean expended = coverList.get(position).isExpended();
        holder.card_txt.setVisibility(expended ? View.VISIBLE : View.GONE);

        if (expended) {
            holder.expend.setImageResource(R.drawable.ic_arrow_drop_up);
        } else {
            holder.expend.setImageResource(R.drawable.ic_arrow_drop_down);
        }

    }

    @Override
    public int getItemCount() {
        return coverList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageButton expend;
        TextView txt, describe;
        CardView card_txt;
        AnimatedCheckBox chooseBox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            expend = itemView.findViewById(R.id.expend);
            chooseBox = itemView.findViewById(R.id.chooseBox);
            txt = itemView.findViewById(R.id.txt);
            describe = itemView.findViewById(R.id.describe);
            card_txt = itemView.findViewById(R.id.card_txt);

            expend.setOnClickListener(v -> {
                Cover cover = coverList.get(getAdapterPosition());
                cover.setExpended(!cover.isExpended());
                notifyItemChanged(getAdapterPosition());
            });

            chooseBox.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                    onCheckedChangeListener.onItemClick(getAdapterPosition(), isChecked);
                }
            });

        }

    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onItemClick(int pos, boolean isChecked);
    }
}
