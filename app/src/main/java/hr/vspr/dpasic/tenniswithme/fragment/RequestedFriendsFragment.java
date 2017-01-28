package hr.vspr.dpasic.tenniswithme.fragment;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.adapter.PendingPlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.adapter.PlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPeopleListFragmentInteractionListener}
 * interface.
 */
public class RequestedFriendsFragment extends AbstractFriendsFragment {

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
    public void prepareListView() {
        friendsPresenter.prepareRequestedListView();
    }

    @Override
    public void updateListViewAdapter(List<Player> players) {
        recyclerView.setAdapter(new PendingPlayerRecyclerViewAdapter(players, ActionType.PENDING_FRIENDSHIP, mListener));
    }
}
