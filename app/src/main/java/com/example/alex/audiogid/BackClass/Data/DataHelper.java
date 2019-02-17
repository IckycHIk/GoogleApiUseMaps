package com.example.alex.audiogid.BackClass.Data;

/**
 * Copyright (C) 2015 Ari C.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataHelper {


    private static List<PlaceWrapper> sPlaceWrappers = new ArrayList<>(Arrays.asList(
            new PlaceWrapper("Киево-Печерская лавра",50.434699,30.557221),
            new PlaceWrapper("Остров Инь-Янь", 48.821557, 25.482301),
            new PlaceWrapper("Лядовский скальный монастырь",48.484885, 27.608011),
            new PlaceWrapper("Яблоня-колония",51.557895, 33.351051),
            new PlaceWrapper("Базальтовые столбы", 50.922680,26.234312),
            new PlaceWrapper("Поющие террасы",50.120564,35.226212),
            new PlaceWrapper("Дземброня",48.105990,24.677620),
            new PlaceWrapper("Бакота", 49.857968,25.642486),
    new PlaceWrapper("Голубая лагуна",48.863456,22.646853),
            new PlaceWrapper("Кагул",45.393290,28.394391)
    ));

    private static List<PlaceSuggestion> sPlaceSuggestions =
            new ArrayList<>(Arrays.asList(
                    new PlaceSuggestion("Киево-Печерская лавра"),
                    new PlaceSuggestion("Остров Инь-Янь"),
                    new PlaceSuggestion("Лядовский скальный монастырь"),
                    new PlaceSuggestion("Яблоня-колония"),
                    new PlaceSuggestion("Базальтовые столбы"),
                    new PlaceSuggestion("Поющие террасы"),
                    new PlaceSuggestion("Дземброня"),
                    new PlaceSuggestion("Бакота"),
                    new PlaceSuggestion("Голубая лагуна"),
                    new PlaceSuggestion("Кагул")));

    public interface OnFindColorsListener {
        void onResults(List<PlaceWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<PlaceSuggestion> results);
    }

    public static List<PlaceSuggestion> getHistory(Context context, int count) {

        List<PlaceSuggestion> suggestionList = new ArrayList<>();
        PlaceSuggestion placeSuggestion;
        for (int i = 0; i < sPlaceSuggestions.size(); i++) {
            placeSuggestion = sPlaceSuggestions.get(i);
            placeSuggestion.setIsHistory(true);
            suggestionList.add(placeSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (PlaceSuggestion placeSuggestion : sPlaceSuggestions) {
            placeSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<PlaceSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (PlaceSuggestion suggestion : sPlaceSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<PlaceSuggestion>() {
                    @Override
                    public int compare(PlaceSuggestion lhs, PlaceSuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<PlaceSuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findColors(Context context, String query, final OnFindColorsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<PlaceWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (PlaceWrapper color : sPlaceWrappers) {
                        if (color.getName().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(color);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<PlaceWrapper>) results.values);
                }
            }
        }.filter(query);

    }





}