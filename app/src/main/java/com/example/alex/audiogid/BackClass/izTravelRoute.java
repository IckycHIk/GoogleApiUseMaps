package com.example.alex.audiogid.BackClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.alex.audiogid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.alex.audiogid.BackClass.JSONParser.json;

public class izTravelRoute {

    Context context;

    public boolean drawRoute(GoogleMap map, Context c, ArrayList<LatLng> points, String language, boolean optimize)
    {

        context = c;


            String url = makeURL("ru","driving");
            new izTravelRoute.connectAsyncTasks(url,false).execute();

            return true;


    }




    public String makeURL (String Languages,String mode){
        StringBuilder urlString = new StringBuilder();

        if(mode == null)
            mode = "driving";

        urlString.append("https://api.izi.travel/");
        urlString.append("&languages=");// from
        urlString.append(Languages);
        String string = String.valueOf(R.string.google_maps_key);
        urlString.append("&api_key="+ "a0f12363-371f-43e6-8bca-337efe17bd56");
        //  urlString.append("&sensor=false&mode="+mode+"&alternatives=true&language="+lang);
        return urlString.toString();
    }


    private class connectAsyncTasks extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        boolean steps;
        connectAsyncTasks(String urlPass, boolean withSteps){
            url = urlPass;
            steps = withSteps;

        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // progressDialog.hide();
            Toast.makeText(context,
                    json, Toast.LENGTH_LONG).show();

            if(result!=null){

            }
        }
    }
/*
    private void drawPath(String  result, boolean withSteps) {


            if(withSteps)
            {
                JSONArray arrayLegs = routes.getJSONArray("legs");
                JSONObject legs = arrayLegs.getJSONObject(0);
                JSONArray stepsArray = legs.getJSONArray("steps");
                //put initial point

                for(int i=0;i<stepsArray.length();i++)
                {
                    Route.Step step = new Route.Step(stepsArray.getJSONObject(i));
                    mMap.addMarker(new MarkerOptions()
                            .position(step.location)
                            .title(step.distance)
                            .snippet(step.instructions)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                }
            }


        }
        catch (JSONException e) {
            Toast.makeText(context,
                    e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    */


}
