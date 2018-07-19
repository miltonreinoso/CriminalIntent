package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Map<UUID, Crime> mCrimes;


    public static CrimeLab get(Context context) {

        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes() {

        return new ArrayList<>(mCrimes.values());
    }


    public Crime getCrime(UUID id){
        return mCrimes.get(id);
    }


    //Singleton Private Constructor
    private CrimeLab(Context context){

        mCrimes = new LinkedHashMap<>();
        for (int i = 1; i < 101; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            if (i%5==0) crime.setRequiresPolice(true); //
            mCrimes.put(crime.getId(), crime);
        }

    }
}
