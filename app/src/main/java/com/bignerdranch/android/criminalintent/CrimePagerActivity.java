package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button mFirstCrimeB;
    private Button mLastCrimeB;



    public static Intent newIntent (Context packageContext, UUID crimeID){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        //Setting up ViewPager's Adapter
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i< mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(crimeID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(mViewPager.getCurrentItem() == 0) {
                    mFirstCrimeB.setVisibility(View.GONE);
                } else {
                    mFirstCrimeB.setVisibility(View.VISIBLE);
                }

                if(mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount()-1) {
                    mLastCrimeB.setVisibility(View.GONE);
                } else {
                    mLastCrimeB.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mFirstCrimeB = findViewById(R.id.go_to_first_button);
        mFirstCrimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mLastCrimeB = findViewById(R.id.go_to_last_button);
        mLastCrimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount()-1);
            }
        });
    }
}
