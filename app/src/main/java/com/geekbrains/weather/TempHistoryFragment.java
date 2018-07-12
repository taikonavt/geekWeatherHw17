package com.geekbrains.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TempHistoryFragment extends BaseFragment{
    RecyclerView recyclerView;

    public TempHistoryFragment() {
    }

    public static TempHistoryFragment newInstance() {
        TempHistoryFragment fragment = new TempHistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //обращаемся к layout который будет содержать наш фрагмент
        return inflater.inflate(R.layout.fragment_temp_history, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvTempHistory);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DataBase db = DataBase.getInstance();

        TempHistoryAdapter adapter = new TempHistoryAdapter(getContext(), db.getTempHistory());
        recyclerView.setAdapter(adapter);
    }
}
