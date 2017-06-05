package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;


/**
 *@author Jheieme Santos da Silveira
 *
 */
public class FgtPrincipal extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View view = null;

    /**
     * construtor da minha view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fgt_principal, container, false);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initComponents();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }


    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initR();
        initData();
    }

    public void onCall(Contato contato) {
        ActPrincipal act = (ActPrincipal) getActivity();
        act.initCall(contato);
    }

    private void initData() {

    }



    private void initR() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getActivity().getSupportFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fgt_principal, container, false);
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
            Fragment fragment = new FgtConversas();

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
