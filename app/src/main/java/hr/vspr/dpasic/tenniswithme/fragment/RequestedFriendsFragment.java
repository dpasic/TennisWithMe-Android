package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.adapter.RequestedFriendsRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsView;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.RequestedFriendsPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnFriendsListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RequestedFriendsFragment extends Fragment implements FriendsView,
        SwipeRefreshLayout.OnRefreshListener {

    private FriendsPresenter friendsPresenter;
    private OnFriendsListFragmentInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.fragment_active_friends_list, container, false);
        ButterKnife.bind(this, view);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeRefreshLayout.setOnRefreshListener(this);

        friendsPresenter = new RequestedFriendsPresenterImpl(this);
        friendsPresenter.prepareListView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFriendsListFragmentInteractionListener) {
            mListener = (OnFriendsListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFriendsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateListViewAdapter(List<User> users) {
        recyclerView.setAdapter(new RequestedFriendsRecyclerViewAdapter(users, mListener));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        friendsPresenter.prepareListView();
    }
}
