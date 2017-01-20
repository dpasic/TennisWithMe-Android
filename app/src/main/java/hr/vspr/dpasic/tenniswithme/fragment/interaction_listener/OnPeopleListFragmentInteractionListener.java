package hr.vspr.dpasic.tenniswithme.fragment.interaction_listener;

/**
 * Created by edjapas on 16.1.2017..
 */

import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnPeopleListFragmentInteractionListener {
    void onListFragmentInteraction(Player item, UserActionType actionType);
}
