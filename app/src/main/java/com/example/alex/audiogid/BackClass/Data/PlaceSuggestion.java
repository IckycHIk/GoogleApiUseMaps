package com.example.alex.audiogid.BackClass.Data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

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

public class PlaceSuggestion implements SearchSuggestion {

    private String mPlaceName;
    private boolean mIsHistory = false;

    public PlaceSuggestion(String suggestion) {
        this.mPlaceName = suggestion.toLowerCase();
    }

    public PlaceSuggestion(Parcel source) {
        this.mPlaceName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mPlaceName;
    }

    public static final Creator<PlaceSuggestion> CREATOR = new Creator<PlaceSuggestion>() {
        @Override
        public PlaceSuggestion createFromParcel(Parcel in) {
            return new PlaceSuggestion(in);
        }

        @Override
        public PlaceSuggestion[] newArray(int size) {
            return new PlaceSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPlaceName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}