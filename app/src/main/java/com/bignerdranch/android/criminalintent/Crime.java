package com.bignerdranch.android.criminalintent;

import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;

    // mTitle set/getters

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    // mId getter

    public UUID getId() {
        return mId;
    }

    public Crime() {
// Generate unique identifier
        mId = UUID.randomUUID();
    }


}
