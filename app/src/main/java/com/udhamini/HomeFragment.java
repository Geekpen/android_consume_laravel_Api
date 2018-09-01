package com.udhamini;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ScholarshipsListAdapter.ItemClickListener{

    ScholarshipsListAdapter adapter;
    ArrayList<Scholarship> scholarships;// = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate( R.layout.fragment_home, container, false );

        pushdata();

        ArrayList<Scholarship> openPositions;
        openPositions = scholarshipslist();





        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        RecyclerView mRecyclerView =  view.findViewById(R.id.scholarships);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        Log.d("debugMode", "The application stopped after this");
        mRecyclerView.setLayoutManager(mLayoutManager);

        

        adapter = new ScholarshipsListAdapter(this, openPositions);
        mRecyclerView.setAdapter(adapter);
        return view;
    }



    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(view.getContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();
        
    }

    private void pushdata(){
        RequestQueue detailsFromApi = Volley.newRequestQueue(getActivity());
        String url = "http://udhamini.com/api/scholarships";
        final String active_token = "Bearer "+SharedPrefManager.getToken();
        final List<Scholarship> scholarships;
        scholarships = new ArrayList<>();
//        scholarships = new ArrayList<>();
//        scholarships.add(new Scholarship(0,"Equity bank scholarship 2018", "2018-11-23"));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.e("dd", response.toString());
                try {
                    // Parsing json object response
                    // response will be a json object
                    //String status_code = response.getString("code");
                    String open_positions = response.getString("open_positions");
                    JSONArray openings = new JSONArray(open_positions);
                    //Log.e("fsaVD", openings.toString());
                    //Scholarships details

                    for(int i=0; i<openings.length(); i++){
                        int id = openings.getJSONObject(i).getInt("id");
                        String title = openings.getJSONObject(i).getString("title");
                        String date_to = openings.getJSONObject(i).getString("date_to");

                        scholarships.add(new Scholarship(id,title, date_to));

                        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(scholarships);
                        editor.putString("scholarship_details", json);
                        editor.commit();

                        // scholarships.add(new Scholarship(id,title, date_to));
                    }

                    //Log.e("Scholarships", scholarships.toString());






                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", active_token);
                return headers;
            }
        };
        detailsFromApi.add(jsonObjReq);
    }

//    public boolean saveId(int id){
//        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("scholarship_id", id);
//        return true;
//    }





    private ArrayList<Scholarship> scholarshipslist(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString( "scholarship_details" , null );
        Type type = new TypeToken<ArrayList<Scholarship>>() {}.getType();
        scholarships = gson.fromJson(json, type);

        if(scholarships == null){
            scholarships = new ArrayList<>(  );
        }

        return scholarships;
    }
}
