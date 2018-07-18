package com.bignerdranch.android.criminalintent;

import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    //ViewHolder
    private class SuperCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public SuperCrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
            super(inflater.inflate(layoutId, parent, false));

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }

        public Crime getCrime() {
            return mCrime;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    getCrime().getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeHolder extends SuperCrimeHolder {
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.list_item_crime);
            itemView.setOnClickListener(this);
        }
    }

    private class PoliceCrimeHolder extends SuperCrimeHolder {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public PoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.require_police_list);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() +
                      " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }
    }

    //Adapter
    private class CrimeAdapter extends RecyclerView.Adapter<SuperCrimeHolder> {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }


        @Override
        public SuperCrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType){
                case 0: return new PoliceCrimeHolder(layoutInflater, parent);
                case 1: return new CrimeHolder(layoutInflater, parent);
                }

                return null;
            }
        @Override
        public void onBindViewHolder(SuperCrimeHolder holder, int position) {

            Crime crime = mCrimes.get(position);
            holder.bind(crime);
            }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice())
            {
                return 0;}
            else return 1;
        }
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

}
