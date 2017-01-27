package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.adapter.PlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsView;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.RequestedFriendsPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPeopleListFragmentInteractionListener}
 * interface.
 */
public class RequestedFriendsFragment extends Fragment implements FriendsView,
        SwipeRefreshLayout.OnRefreshListener {

    private FriendsPresenter friendsPresenter;
    private OnPeopleListFragmentInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequestedFriendsFragment() {
    }

    public static RequestedFriendsFragment newInstance() {
        RequestedFriendsFragment fragment = new RequestedFriendsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requested_friends_list, container, false);
        ButterKnife.bind(this, view);

        Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, layoutManager.getOrientation()));

        swipeRefreshLayout.setOnRefreshListener(this);

        friendsPresenter = new RequestedFriendsPresenterImpl(this);
        friendsPresenter.prepareListView();

        return view;
    }

    @Override
    public void updateListViewAdapter(List<Player> players) {
        recyclerView.setAdapter(new PlayerRecyclerViewAdapter(players, ActionType.CONFIRM_FRIENDSHIP, mListener));
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

    @OnClick(R.id.fab_add)
    public void editUserInfoClick() {
        Fragment fragment = FriendRequestFragment.newInstance();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, FriendRequestFragment.class.getName())
                .addToBackStack(FriendRequestFragment.class.getName()).commit();

        getActivity().setTitle(R.string.title_request_friendship);
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
