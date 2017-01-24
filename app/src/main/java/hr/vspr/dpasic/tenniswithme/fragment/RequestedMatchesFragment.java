package hr.vspr.dpasic.tenniswithme.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.adapter.MatchRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Match;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnMatchListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestedMatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestedMatchesFragment extends AbstractMatchesFragment {

    public RequestedMatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActiveMatchesFragment.
     */
    public static RequestedMatchesFragment newInstance() {
        RequestedMatchesFragment fragment = new RequestedMatchesFragment();
        return fragment;
    }

    @Override
    public void prepareListView() {
        matchesPresenter.prepareRequiredListView();
    }

    @Override
    public void updateListViewAdapter(List<Match> matches) {
        recyclerView.setAdapter(new MatchRecyclerViewAdapter(getContext(), matches, ActionType.CONFIRM_MATCH, mListener));
    }
}
