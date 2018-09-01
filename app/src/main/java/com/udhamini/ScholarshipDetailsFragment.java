package com.udhamini;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScholarshipDetailsFragment extends Fragment {

    public String title;
    public String description;
    public String lvStudy;
    public String prType;
    public String countryN;
    public String subCTitle;
    public String subCName;
    public String distTitle;
    public String distName;
    public String dueDate;
    TextView tTitle;


    public ScholarshipDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate( R.layout.fragment_scholarship_details, container, false );

        tTitle =(TextView) view.findViewById(R.id.titleString);

        new thisDetails().execute();

        return view;
    }



    private class thisDetails  extends AsyncTask<Void, Void, Void> {

        /**
         * `doInBackground` is run on a separate, background thread
         * (not on the main/ui thread). DO NOT try to update the ui
         * from here.
         */
        @Override
        protected Void doInBackground(Void... params) {
            final int thisId = SharedPrefManager.getScholarshipId();
            RequestQueue detailsFromApi = Volley.newRequestQueue(getActivity());
            String url = String.format("http://udhamini.com/api/scholarship-data?scholarship_id=%1$s",thisId);
            //String url = "http://udhamini.com/api/scholarship-data";
            final String active_token = "Bearer "+SharedPrefManager.getToken();

//        scholarships = new ArrayList<>();
//        scholarships.add(new Scholarship(0,"Equity bank scholarship 2018", "2018-11-23"));

            JsonObjectRequest jsonObjReq = new JsonObjectRequest( Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.e("dd", response.toString());
                    try {
                        // Parsing json object response
                        // response will be a json object
                        //String status_code = response.getString("code");
                        String scholarship = response.getString("scholarship");

                        JSONObject data = new JSONObject(scholarship);
                        title = data.getString("title");
                        tTitle.setText(title);
                        //SharedPrefManager.writeScholarshipTitle(title);


                        description = data.getString("description");


                        dueDate = data.getString("date_to");


                        String level_of_study = response.getString("level_of_study");
                        JSONObject lStudy = new JSONObject(level_of_study);
                        lvStudy = lStudy.getString("level_of_study");


                        String program_type = response.getString("program_type");
                        JSONObject pgType = new JSONObject(program_type);
                        prType = pgType.getString("program_type");


                        String country = response.getString("country");
                        JSONObject nameCountry = new JSONObject(country);
                        countryN = nameCountry.getString("name");

                        String statesProvince = response.getString("statesprovince");
                        JSONObject sProvince = new JSONObject(statesProvince);
                        subCTitle = sProvince.getString("description");

                        String sub1 = response.getString("sub1");
                        JSONObject sub1data = new JSONObject(sub1);
                        subCName = sub1data.getString("subadm1_name");

                        String districtSubCounty = response.getString("districtsubcounty");
                        JSONObject dsData = new JSONObject(districtSubCounty);
                        distTitle = dsData.getString("description");



                        String sub2 = response.getString("sub2");
                        JSONObject sub2data = new JSONObject(sub2);
                        distName = sub2data.getString("subadm2_name");

                        Log.d( TAG, distName );


                        //JSONObject openings = new JSONObject(open_positions);
                        //                   Log.e("title", title.toString());
//                    Log.e("level_of_study", level_of_study.toString());
//                    Log.e("program_type", program_type.toString());
//                    Log.e("country", country.toString());
//                    Log.e("sub1", sub1.toString());
//                    Log.e("sub2", sub2.toString());
//                    Log.e("statesProvince", statesProvince.toString());
//                    Log.e("districtSubCounty", districtSubCounty.toString());
                        //Scholarships details

//                    for(int i=0; i<openings.length(); i++){
//                        int id = openings.getJSONObject(i).getInt("id");
//                        String title = openings.getJSONObject(i).getString("title");
//                        String date_to = openings.getJSONObject(i).getString("date_to");
//                    }

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
            //Log.d( TAG, "post execute" );
            return null;
        }

        /**
         * `onPostExecute` is run after `doInBackground`, and it's
         * run on the main/ui thread, so you it's safe to update ui
         * components from it. (this is the correct way to update ui
         * components.)
         */
        @Override
        protected void onPostExecute(Void param) {

            //cleanupUiAfterCancelOrDelete();
            //galleryItemAdapter.notifyDataSetChanged();
        }
    }


    public void readWrite(){
        final int thisId = SharedPrefManager.getScholarshipId();
        RequestQueue detailsFromApi = Volley.newRequestQueue(getActivity());
        String url = String.format("http://udhamini.com/api/scholarship-data?scholarship_id=%1$s",thisId);
        //String url = "http://udhamini.com/api/scholarship-data";
        final String active_token = "Bearer "+SharedPrefManager.getToken();

//        scholarships = new ArrayList<>();
//        scholarships.add(new Scholarship(0,"Equity bank scholarship 2018", "2018-11-23"));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest( Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.e("dd", response.toString());
                try {
                    // Parsing json object response
                    // response will be a json object
                    //String status_code = response.getString("code");
                    String scholarship = response.getString("scholarship");

                    JSONObject data = new JSONObject(scholarship);
                    title = data.getString("title");

                    //SharedPrefManager.writeScholarshipTitle(title);


                    description = data.getString("description");


                    dueDate = data.getString("date_to");


                    String level_of_study = response.getString("level_of_study");
                    JSONObject lStudy = new JSONObject(level_of_study);
                    lvStudy = lStudy.getString("level_of_study");


                    String program_type = response.getString("program_type");
                    JSONObject pgType = new JSONObject(program_type);
                    prType = pgType.getString("program_type");


                    String country = response.getString("country");
                    JSONObject nameCountry = new JSONObject(country);
                    countryN = nameCountry.getString("name");

                    String statesProvince = response.getString("statesprovince");
                    JSONObject sProvince = new JSONObject(statesProvince);
                    subCTitle = sProvince.getString("description");

                    String sub1 = response.getString("sub1");
                    JSONObject sub1data = new JSONObject(sub1);
                    subCName = sub1data.getString("subadm1_name");

                    String districtSubCounty = response.getString("districtsubcounty");
                    JSONObject dsData = new JSONObject(districtSubCounty);
                    distTitle = dsData.getString("description");



                    String sub2 = response.getString("sub2");
                    JSONObject sub2data = new JSONObject(sub2);
                    distName = sub2data.getString("subadm2_name");

                    Log.d( TAG, distName );


                    //JSONObject openings = new JSONObject(open_positions);
                    //                   Log.e("title", title.toString());
//                    Log.e("level_of_study", level_of_study.toString());
//                    Log.e("program_type", program_type.toString());
//                    Log.e("country", country.toString());
//                    Log.e("sub1", sub1.toString());
//                    Log.e("sub2", sub2.toString());
//                    Log.e("statesProvince", statesProvince.toString());
//                    Log.e("districtSubCounty", districtSubCounty.toString());
                    //Scholarships details

//                    for(int i=0; i<openings.length(); i++){
//                        int id = openings.getJSONObject(i).getInt("id");
//                        String title = openings.getJSONObject(i).getString("title");
//                        String date_to = openings.getJSONObject(i).getString("date_to");
//                    }

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






}
