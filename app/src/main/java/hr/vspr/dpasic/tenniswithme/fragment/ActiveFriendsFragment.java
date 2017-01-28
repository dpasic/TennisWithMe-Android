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

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.adapter.PlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsView;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPeopleListFragmentInteractionListener}
 * interface.
 */
public class ActiveFriendsFragment extends AbstractFriendsFragment {

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
    public void prepareListView() {
        friendsPresenter.prepareActiveListView();
    }

    @Override
    public void updateListViewAdapter(List<Player> players) {
        recyclerView.setAdapter(new PlayerRecyclerViewAdapter(players, ActionType.REQUEST_MATCH, mListener));
    }
}
