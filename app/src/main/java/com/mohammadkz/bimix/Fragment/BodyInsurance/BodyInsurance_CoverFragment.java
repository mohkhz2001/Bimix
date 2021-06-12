package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Adapter.ChooseBoxAdapter;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.Model.Cover;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class BodyInsurance_CoverFragment extends Fragment {

    private BodyInsurance bodyInsurance;
    View view;
    RecyclerView recyclerView;
    ArrayList<String> coverList = new ArrayList<>();
    Button next;
    ArrayList<Cover> covers = new ArrayList<>();

    public BodyInsurance_CoverFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_cover, container, false);

        initViews();
        controllerViews();
        setAdapter();

        return view;
    }

    private void initViews() {

        recyclerView = view.findViewById(R.id.coverList);

        next = view.findViewById(R.id.next);
    }

    private void controllerViews() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BodyInsuranceActivity) getActivity()).setSeekBar(3);
                bodyInsurance.setCover(coverList);
                BodyInsurance_OffFragment bodyInsurance_offFragment = new BodyInsurance_OffFragment(bodyInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_offFragment).commit();
            }
        });

    }

    private void setAdapter() {

        covers.add(new Cover("غرامت عدم استفاده از خودرو در زمان تعمیر", "پرداخت غرامت در زمان تعمیر خودرو در نمایندگی های مجاز"));
        covers.add(new Cover("نوسان قیمت وسیله نقلیه تا 25 درصد", "اگر قیمت خودرو افزایش پیدا کن تا 25 درصد قیمت خودرود که در بیمه نامه قید شده نیاز به الحاقیه نیست."));
        covers.add(new Cover("نوسان قیمت وسیله نقلیه تا 50 درصد", "اگر قیمت خودرو افزایش پیدا کن تا 50 درصد قیمت خودرود که در بیمه نامه قید شده نیاز به الحاقیه نیست."));
        covers.add(new Cover("نوسان قیمت وسیله نقلیه تا 100 درصد", "اگر قیمت خودرو افزایش پیدا کن تا 100 درصد قیمت خودرود که در بیمه نامه قید شده نیاز به الحاقیه نیست."));
        covers.add(new Cover("سرقت درجای کلیه قطعات خودرو", "سرقت قطعات خودرو"));
        covers.add(new Cover("بلایای طبیعی", ""));


        ChooseBoxAdapter chooseBoxAdapter = new ChooseBoxAdapter(getContext(), covers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(chooseBoxAdapter);

        chooseBoxAdapter.setOnCheckedChangeListener(new ChooseBoxAdapter.OnCheckedChangeListener() {
            @Override
            public void onItemClick(int pos, boolean isChecked) {

                covers.get(pos).setChecked(isChecked);
                if (isChecked) {
                    coverList.add(covers.get(pos).getText());
                } else {
                    int n = coverList.indexOf(covers.get(pos).getText().toString());
                    coverList.remove(n);
                }

            }
        });


    }

}