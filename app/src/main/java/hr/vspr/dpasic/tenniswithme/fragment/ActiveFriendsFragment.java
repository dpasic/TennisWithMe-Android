package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.adapter.PeopleRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.ActiveFriendsPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsView;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPeopleListFragmentInteractionListener}
 * interface.
 */
public class ActiveFriendsFragment extends Fragment implements FriendsView,
        SwipeRefreshLayout.OnRefreshListener {

    private FriendsPresenter friendsPresenter;
    private OnPeopleListFragmentInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActiveFriendsFragment() {
    }

    public static ActiveFriendsFragment newInstance() {
        ActiveFriendsFragment fragment = new ActiveFriendsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_friends_list, container, false);
        ButterKnife.bind(this, view);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeRefreshLayout.setOnRefreshListener(this);

        friendsPresenter = new ActiveFriendsPresenterImpl(this);
        friendsPresenter.prepareListView();

        return view;
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

    @Override
    public void updateListViewAdapter(List<Player> players) {
        recyclerView.setAdapter(new PeopleRecyclerViewAdapter(players, UserActionType.REQUEST_MATCH, mListener));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        friendsPresenter.prepareListView();
    }


    @Override
    public void notifyRequestError(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, msg, Snackbar.LENGTH_LONG);
    }
}
