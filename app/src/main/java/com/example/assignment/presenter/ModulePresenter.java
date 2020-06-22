package com.example.assignment.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.assignment.module.Modules;
import com.example.assignment.utilities.Constants;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class ModulePresenter {

    private Context context;
    private moduleListListener moduleListListener;

    public ModulePresenter(Context context, ModulePresenter.moduleListListener moduleListListener) {
        this.context = context;
        this.moduleListListener = moduleListListener;
    }

    public interface moduleListListener{

        void success(List<Modules> modulesList);
        void error(String tag);
        void failure(String tag);

        void success(int id, String modules);
    }

    public void getModuleList(){

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.MODULE_LIST_API,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        if (response.length() >0){
                            List<Modules> modules = Arrays.asList(gson.fromJson(response.toString(), Modules[].class));
                            moduleListListener.success(modules);
                            Log.d("TAG", "onResponse: "+response.toString());
                        }else {
                            moduleListListener.error("Response not available");
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        moduleListListener.failure(error.getMessage());
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }




}
