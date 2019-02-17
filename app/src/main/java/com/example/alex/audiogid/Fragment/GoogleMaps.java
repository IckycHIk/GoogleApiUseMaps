package com.example.alex.audiogid.Fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.example.alex.audiogid.BackClass.Adapter.SearchResultsListAdapter;
import com.example.alex.audiogid.BackClass.Data.PlaceSuggestion;
import com.example.alex.audiogid.BackClass.Data.PlaceWrapper;
import com.example.alex.audiogid.BackClass.Data.DataHelper;
import com.example.alex.audiogid.BackClass.GoogleMaps.Helpers.NtConDetector;
import com.example.alex.audiogid.BackClass.GoogleMaps.Helpers.RestClient;
import com.example.alex.audiogid.BackClass.Route;
import com.example.alex.audiogid.Interfac.setMarker;
import com.example.alex.audiogid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.annotations.Expose;


import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.alex.audiogid.BackClass.Data.DataHelper.resetSuggestionsHistory;


public class GoogleMaps extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, setMarker {
    private View view;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FragmentActivity myContext;
    private ArrayList<LatLng> markerPoints;
    private FloatingSearchView mSearchView;

    private SearchResultsListAdapter mSearchResultsAdapter;

    private String mapsApiKey = "AIzaSyC4jrsYBpnTE7KSdhRGRhE325pbTGemZtU";
    private int width;
    private Route rt;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PLACE_PICKER_REQUEST = 1;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;
    private AutoCompleteTextView mSearchText;



    private AutoCompleteTextView atvCity;
    private String apiKey = "AIzaSyAjy9cevZ9QNlXo0BRRyA0D4qEU8uqp0_0"; //Add your Server API Key

    private ArrayAdapter<String> adapter;
    private NtConDetector ncd;
    private AlertDialog.Builder alertDialogBuilder;
    private Context Activity_context;
    private AlertDialog alertDialog;
    private Toast Querytoast;
    private String mLastQuery = "";


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.googlemap_fragment, container, false);
        myContext = getActivity();


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


//AutoComplete


        width = getResources().getDisplayMetrics().widthPixels;

//Permissions





// Create a new Places client instance.
//SearchView

        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<PlaceSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

            }
        });
        resetSuggestionsHistory();
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                PlaceSuggestion placeSuggestion = (PlaceSuggestion) searchSuggestion;
                DataHelper.findColors(getActivity(), placeSuggestion.getBody(),
                        new DataHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<PlaceWrapper> results) {
getPoint(results.get(0).getFirstLtn(),(results.get(0).getSecondLtn()));
                            }

                        });

                mSearchView.setSearchFocused(false);
                mSearchView.setSearchText("");
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;

                DataHelper.findColors(getActivity(), query,
                        new DataHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<PlaceWrapper> results) {
                                mSearchResultsAdapter.swapData(results);
                            }

                        });

            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));


            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());


            }
        });


        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {


            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {


            }
        });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item after as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load the left icon images using your favorite image loading library, or change text color.
         *
         *
         * Important:
         * Keep in mind that the suggestion list is a RecyclerView, so views are reused for different
         * items in the list.
         */
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                PlaceSuggestion placeSuggestion = (PlaceSuggestion) item;

                String textColor =  "#000000";
                String textLight = "#787878";

                if (placeSuggestion.getIsHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }

                textView.setTextColor(Color.parseColor(textColor));
                String text = placeSuggestion.getBody()
                        .replaceFirst(mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }

        });

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {

            }
        });

        /*
         * When the user types some text into the search field, a clear button (and 'x' to the
         * right) of the search text is shown.
         *
         * This listener provides a callback for when this button is clicked.
         */
        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {


            }
        });







        ncd = new NtConDetector(); //Initialize NtConDetector- To Detect Internet Connection

        Activity_context = myContext;//Initialize Context

        // Only 1000 requests for a Server API key per Day. So we intimate to the User when Query limit reached .
        Querytoast = Toast.makeText(myContext, "API-Query limit reached. Try tommorrow", Toast.LENGTH_LONG);


                  //  ShowNoInternetDialog();



        // To get the text of Clicked Item.


        // As like above methods for Search City - EditText


        mGoogleApiClient = new GoogleApiClient
                .Builder(myContext)
                .enableAutoManage(myContext, 0, this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();

        displayPlacePicker();

        // Initializing

        markerPoints = new ArrayList<>();

        return view;
    }





    private void CallMapsApiCity(CharSequence s) {

        RestClient.get().autocompleteCity(apiKey, "(cities)", s.toString(),
                new Callback<PlacesResult>() {
                    @Override
                    public void success(final PlacesResult placesResult, Response res) {
                        if (placesResult.status.equals("OK")) {
                            List<String> strings = new ArrayList<String>();
                            for (Prediction p : placesResult.predictions) {
                                strings.add(p.description);
                            }
                            adapter = new ArrayAdapter<String>(myContext, android.R.layout.simple_list_item_1, strings);
                            atvCity.setAdapter(adapter);
                        } else if (placesResult.status.equals("OVER_QUERY_LIMIT")) {
                            ShowQueryToast();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }




    //Request the Maps Places Api for Places (or) Address
    private void CallMapsApiPlace(CharSequence s) {

        RestClient.get().autocompletePlace(apiKey, s.toString(),
                new Callback<PlacesResult>() {
                    @Override
                    public void success(final PlacesResult placesResult, Response res) {
                        if (placesResult.status.equals("OK")) {
                            List<String> strings = new ArrayList<String>();
                            for (Prediction p : placesResult.predictions) {
                                strings.add(p.description);
                            }
                        } else if (placesResult.status.equals("OVER_QUERY_LIMIT")) {
                            ShowQueryToast();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }

    @Override
    public void getPoint(double LatLng, double LatLat) {
        MarkerOptions markerOptions = new MarkerOptions();

   LatLng  latLng = new LatLng(LatLng,LatLat);
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Clears the previously touched position

        // Animating to the touched position
        LatLng coordinate = latLng; //Store these lat lng values somewhere. These should be constant.
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                coordinate, 10);
        mMap.animateCamera(location);
        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    // Get the Response - places result
    public class PlacesResult {
        @Expose
        List<Prediction> predictions;
        @Expose
        String status;
    }

    public class Prediction {
        @Expose
        String description;
    }

    //Show no Intenet dialog & show the wifi and mobile data to connect the internet
    public void ShowNoInternetDialog() {
        alertDialogBuilder = new AlertDialog.Builder(Activity_context);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(Activity_context.getResources().getString(R.string.noInternet_title));
        alertDialogBuilder.setMessage(Activity_context.getResources().getString(R.string.noInternet_msg));
        alertDialogBuilder.setPositiveButton(Activity_context.getResources().getString(R.string.mobile_data), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(
                            "com.android.settings",
                            "com.android.settings.Settings$DataUsageSummaryActivity"));
                    Activity_context.startActivity(intent);
                } catch (Exception e) {
                    Log.v("Exception", "Alertdialog setting " + e);
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    Activity_context.startActivity(intent);
                }
                alertDialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton(Activity_context.getResources().getString(R.string.wifi), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                Activity_context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialogBuilder.setNeutralButton(Activity_context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Show the Query limit reached Toast.
    public void ShowQueryToast() {
        if (Querytoast != null) {
            Querytoast.show();
        }
    }


    private void displayPlacePicker() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(myContext), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }



    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }
    private void hideSoftKeyboard(){
        myContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
                Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (myLocation == null) {
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    String provider = lm.getBestProvider(criteria, true);
                    myLocation = lm.getLastKnownLocation(provider);
                }
                if (myLocation != null) {
                    LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                }

            LocationManager locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5, 1, new LocationListener(){
                        @Override
                        public void onLocationChanged(Location loc) {
                            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
                            mMap.moveCamera(center);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                    // Already two locations
                    if(markerPoints.size()>1){
                        markerPoints.clear();
                        mMap.clear();
                    }

                    // Adding new item to the ArrayList
                    markerPoints.add(latLng);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(latLng);

                    /**
                     * For the start location, the color of marker is GREEN and
                     * for the end location, the color of marker is RED.
                     */
                    if(markerPoints.size()==1){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }else if(markerPoints.size()==2){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    // Add new marker to the Google Map Android API V2
                    mMap.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if(markerPoints.size() >= 2){
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);
                               rt = new Route();

                        if (origin == null) {
                            Toast.makeText(myContext,
                                    "Please select your Source first...",
                                    Toast.LENGTH_LONG).show();
                        } else if (origin.equals(dest)) {
                            Toast.makeText(myContext,
                                    "Source and Destinatin can not be the same..",
                                    Toast.LENGTH_LONG).show();
                        } else {


                                rt.drawRoute(mMap, myContext,origin , dest, false, "en");


                        }
                    }
                }


        });

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    //  --------------------------- google places API autocomplete suggestions -----------------




}


