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


public class PlaceWrapper {
    private String Name;
    private double FirstLtn;
    private double SecondLtn;

    public PlaceWrapper(String name, double firstLtn, double secondLtn) {
        Name = name;
        FirstLtn = firstLtn;
        SecondLtn = secondLtn;
    }

    public double getFirstLtn() {
        return FirstLtn;
    }

    public void setFirstLtn(double firstLtn) {
        FirstLtn = firstLtn;
    }

    public double getSecondLtn() {
        return SecondLtn;
    }

    public void setSecondLtn(double secondLtn) {
        SecondLtn = secondLtn;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}