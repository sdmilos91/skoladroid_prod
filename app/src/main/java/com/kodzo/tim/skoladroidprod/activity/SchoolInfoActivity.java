package com.kodzo.tim.skoladroidprod.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.fragment.SchoolsFilterFragment;
import com.kodzo.tim.skoladroidprod.fragment.SchoolsRecensionFragment;
import com.kodzo.tim.skoladroidprod.model.School;
import com.kodzo.tim.skoladroidprod.task.GetRecensionsTaks;
import com.kodzo.tim.skoladroidprod.util.Mokap;


public class SchoolInfoActivity extends AppCompatActivity implements SchoolsRecensionFragment.RecensionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static int position = 1;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static ListView mCommentList;
    private static View view;
    private static FragmentActivity activity;
    private static int mSchoolId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        position = getIntent().getIntExtra("schoolPosition", 1); //dodato
        position++;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager manager = getFragmentManager();
                android.app.Fragment frag = manager.findFragmentByTag("fragment_recension");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                mSchoolId = Integer.parseInt(Mokap.getSchools().get(position -1).getId());
                SchoolsRecensionFragment recDialog = new SchoolsRecensionFragment(mSchoolId);
                recDialog.show(manager, "fragment_recension");

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });



        toolbar.setTitle(Mokap.getSchools().get(position - 1).getName());
        toolbar.setSubtitle(Mokap.getSchools().get(position - 1).getType());
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecension() {
        GetRecensionsTaks task = new GetRecensionsTaks(activity, view, mCommentList);
        String url = "http://milos91ns-001-site1.dtempurl.com/skoladroid/getrecension?schoolId=" + mSchoolId;
        task.execute(url);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int page = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView = null;
            School school = Mokap.getSchools().get(position -1);
            activity = getActivity();
            if(page == 1)
            {
                rootView = inflater.inflate(R.layout.fragment_school_info, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.sectionSchoolName);
                textView.setText(school.getName());

                TextView textViewCity = (TextView) rootView.findViewById(R.id.sectionSchoolCity);
                textViewCity.setText(school.getCity());

                TextView textViewCounty = (TextView) rootView.findViewById(R.id.schoolCounty);
                textViewCounty.setText(school.getCounty());

                TextView textViewAddress = (TextView) rootView.findViewById(R.id.schoolAddress);
                textViewAddress.setText(school.getAddress());

                TextView textViewMunicipality = (TextView) rootView.findViewById(R.id.schoolMunicipality);
                textViewMunicipality.setText(school.getMunicipality());

                TextView textViewPhone = (TextView) rootView.findViewById(R.id.schoolPhone);
                textViewPhone.setText(school.getPhone());

                TextView textViewFax = (TextView) rootView.findViewById(R.id.schoolFax);
                textViewFax.setText(school.getFax());

                TextView textViewPostCode = (TextView) rootView.findViewById(R.id.schoolPostCode);
                textViewPostCode.setText(school.getPostCode());

                TextView textViewSite= (TextView) rootView.findViewById(R.id.schoolSite);
                textViewSite.setText(school.getSite());

                TextView textViewDepartments = (TextView) rootView.findViewById(R.id.schoolDepartments);
                textViewDepartments.setText(school.getDepartments());

                TextView textViewType = (TextView) rootView.findViewById(R.id.schoolType);
                textViewType.setText(school.getType());

            }
            else {
                rootView = inflater.inflate(R.layout.fragment_school_recension, container, false);
                mCommentList = (ListView)rootView.findViewById(R.id.commentList);
                GetRecensionsTaks task = new GetRecensionsTaks(getActivity(), rootView, mCommentList);
                String url = "http://milos91ns-001-site1.dtempurl.com/skoladroid/getrecension?schoolId=" + school.getId();
                task.execute(url);
                /*TextView textView = (TextView) rootView.findViewById(R.id.sectionSchoolName);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
            }

            view = rootView;
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Информције";
                case 1:
                    return "Рецензије";
            }
            return null;
        }
    }
}
