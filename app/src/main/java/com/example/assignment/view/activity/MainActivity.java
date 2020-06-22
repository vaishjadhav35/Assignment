package com.example.assignment.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.assignment.R;
import com.example.assignment.module.Modules;
import com.example.assignment.presenter.ModulePresenter;
import com.example.assignment.utilities.Utils;
import com.example.assignment.view.adapter.AdapterModuleList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
         implements ModulePresenter.moduleListListener{

        @BindView(R.id.rv_module_list)
        RecyclerView rvModuleList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            fetchData();

        }

        private void fetchData() {
            ModulePresenter modulePresenter = new ModulePresenter(this, this);
            modulePresenter.getModuleList();
        }

        @Override
        public void success(List<Modules> modulesList) {
            //set adapter here
            AdapterModuleList adapterModuleList = new AdapterModuleList(this, modulesList);
            rvModuleList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvModuleList.setLayoutManager(layoutManager);
            rvModuleList.setAdapter(adapterModuleList);
        }

        @Override
        public void error(String tag) {
          Utils.showAlert(this, tag);
        }

        @Override
        public void failure(String tag) {
            Utils.showAlert(this, "Failure");
        }

        @Override
        public void success(int id, String modules) {
            //No use here
        }

    }
