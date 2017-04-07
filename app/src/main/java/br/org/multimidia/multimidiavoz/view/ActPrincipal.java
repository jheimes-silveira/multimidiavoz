package br.org.multimidia.multimidiavoz.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.enuns.Action;
import br.org.multimidia.multimidiavoz.utils.MobileApp;
import br.org.multimidia.multimidiavoz.utils.Router;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActPrincipal extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Context context;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);
        context = ActPrincipal.this;
        if (MobileApp.getApplication().getContatoLogado(context) == null) {
           finish();
        }
        Bundle args = getIntent().getExtras();
        if (args != null && args.containsKey(Action.MESSAGE.toString())) {
            Utils.showToast(context, args.get(Action.MESSAGE.toString()).toString());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // Set up the ViewPager with the sections adapter.
        toolbar.inflateMenu(R.menu.menu_act_principal);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_principal, menu);
        return true;
    }


    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fgt_principal, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if(id == R.id.btn_deslogar) {
            MobileApp.getApplication().logout(context);
            Router.onCreateActivity(context, ActLogin.class);
            finish();
        }
        return true;
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
            Fragment fragment = new FgtConversas();
            if (position == 0) {
                fragment = new FgtConversas();
            } else if (position == 1) {
                fragment = new FgtContatos();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Conversas";
                case 1:
                    return "Contatos";
            }
            return null;
        }
    }
}
