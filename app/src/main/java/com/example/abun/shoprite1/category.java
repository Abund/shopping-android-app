package com.example.abun.shoprite1;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

public class category extends Fragment {
    View view;
    public TextView beverages;
    public TextView personal;
    public TextView groceries;
    public TextView household;
    public TextView electronics;
    private FragmentToActivity mCallback;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_category,container,false);

        beverages = (TextView) view.findViewById(R.id.Beverages);
        personal = (TextView) view.findViewById(R.id.Personal);
        groceries = (TextView) view.findViewById(R.id.Groceries);
        household = (TextView) view.findViewById(R.id.Households);
        electronics = (TextView) view.findViewById(R.id.Electronics);

        beverages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(getActivity().getBaseContext(), ProductCatergory.class);
                at.putExtra("coding","1");
                startActivity(at);
            }
        });
        personal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(getActivity().getBaseContext(), ProductCatergory.class);
                at.putExtra("coding","2");
                startActivity(at);
            }
        });
        groceries.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(getActivity().getBaseContext(), ProductCatergory.class);
                at.putExtra("coding","5");
                startActivity(at);
            }
        });
        household.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(getActivity().getBaseContext(), ProductCatergory.class);
                at.putExtra("coding","3");
                startActivity(at);
            }
        });
        electronics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(getActivity().getBaseContext(), ProductCatergory.class);
                at.putExtra("coding","4");
                startActivity(at);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    private void sendData(int comm)
    {
        mCallback.communicate(comm);

    }
    public void onRefresh() {
        Toast.makeText(getActivity(), "Fragment : Refresh called.",
                Toast.LENGTH_SHORT).show();
    }
}
