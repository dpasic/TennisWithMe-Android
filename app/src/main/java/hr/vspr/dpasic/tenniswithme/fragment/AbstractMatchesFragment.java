package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnMatchListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.fragment.matches_mvp.MatchesPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.matches_mvp.MatchesPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.matches_mvp.MatchesView;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by edjapas on 24.1.2017..
 */

public abstract class AbstractMatchesFragment extends Fragment implements MatchesView,
    SwipeRefreshLayout.OnRefreshListener {

    protected MatchesPresenter matchesPresenter;
    protected OnMatchListFragmentInteractionListener mListener;
    protected Player loginPlayer;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matches_list, container, false);
        ButterKnife.bind(this, view);
        setTitle();

        Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, layoutManager.getOrientation()));

        swipeRefreshLayout.setOnRefreshListener(this);

        loginPlayer = SQLite.select().from(Player.class).querySingle();

        matchesPresenter = new MatchesPresenterImpl(this);
        prepareListView();

        return view;
    }

    public abstract void setTitle();
    public abstract void prepareListView();
    public abstract void updateListViewAdapter(List<Match> matches);

    @Override
    public void updateListView(List<Match> matches) {
        updateListViewAdapter(matches);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void notifyRequestError(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, msg, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onRefresh() {
        prepareListView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMatchListFragmentInteractionListener) {
            mListener = (OnMatchListFragmentInteractionListener) context;
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