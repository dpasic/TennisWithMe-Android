package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.adapter.PeopleRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.fragment.search_partners_mvp.SearchPartnersPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.search_partners_mvp.SearchPartnersPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.search_partners_mvp.SearchPartnersView;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPeopleListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchPartnersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPartnersFragment extends Fragment implements SearchPartnersView {

    private SearchPartnersPresenter searchPartnersPresenter;
    private OnPeopleListFragmentInteractionListener mListener;

    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.spinner_genders)
    Spinner spinnerGenders;
    @BindView(R.id.spinner_skills)
    Spinner spinnerSkills;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.players_list)
    RecyclerView recyclerView;
    @BindView(R.id.search_partners_view)
    FrameLayout searchPartnersView;

    public SearchPartnersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchPartnersFragment.
     */
    public static SearchPartnersFragment newInstance() {
        SearchPartnersFragment fragment = new SearchPartnersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_partners, container, false);
        ButterKnife.bind(this, view);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        searchPartnersPresenter = new SearchPartnersPresenterImpl(this);

        return view;
    }

    @OnClick(R.id.btn_search_partners)
    public void searchPartnersClick() {
        String city = etCity.getText().toString();

        String all = getString(R.string.all);
        String gender = spinnerGenders.getSelectedItem().toString();
        gender = gender.equals(all) ? "" : gender;

        String skill = spinnerSkills.getSelectedItem().toString();
        skill = skill.equals(all) ? "" : skill;

        loadingProgress.setVisibility(View.VISIBLE);
        searchPartnersPresenter.searchPartnersByQueries(city, gender, skill);
    }

    @Override
    public void updateListViewAdapter(List<Player> players) {
        loadingProgress.setVisibility(View.GONE);
        recyclerView.setAdapter(new PeopleRecyclerViewAdapter(players, UserActionType.REQUEST_MATCH, mListener));
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(searchPartnersView, msg, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPeopleListFragmentInteractionListener) {
            mListener = (OnPeopleListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPeopleListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
