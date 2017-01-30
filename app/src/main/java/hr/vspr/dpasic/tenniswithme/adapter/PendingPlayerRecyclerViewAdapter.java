package hr.vspr.dpasic.tenniswithme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Player} and makes a call to the
 * specified {@link OnPeopleListFragmentInteractionListener}.
 */
public class PendingPlayerRecyclerViewAdapter extends RecyclerView.Adapter<PendingPlayerRecyclerViewAdapter.ViewHolder> {

    private final List<Player> mValues;
    private final ActionType mActionType;
    private final OnPeopleListFragmentInteractionListener mListener;

    public PendingPlayerRecyclerViewAdapter(List<Player> items, ActionType actionType, OnPeopleListFragmentInteractionListener listener) {
        mValues = items;
        mActionType = actionType;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        final boolean isFriendshipReceived = holder.mItem.isFriendshipReceived();

        if (isFriendshipReceived) {
            holder.mPendingView.setImageResource(R.mipmap.confirm_friendship);
        } else {
            holder.mPendingView.setImageResource(R.mipmap.sent_request);
        }

        holder.mFullName.setText(holder.mItem.getFullName());
        holder.mSkill.setText(holder.mItem.getSkill());
        holder.mGender.setText(holder.mItem.getGender());
        holder.mSummary.setText(holder.mItem.getSummary());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (isFriendshipReceived) {
                        mListener.onListFragmentInteraction(holder.mItem, ActionType.CONFIRM_FRIENDSHIP);
                    } else {
                        mListener.onListFragmentInteraction(holder.mItem, ActionType.REQUEST_MATCH);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public Player mItem;

        public final View mView;
        public final TextView mFullName;
        public final TextView mSkill;
        public final TextView mGender;
        public final TextView mSummary;
        public final ImageView mPendingView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFullName = (TextView) view.findViewById(R.id.tv_full_name);
            mSkill = (TextView) view.findViewById(R.id.tv_skill);
            mGender = (TextView) view.findViewById(R.id.tv_gender);
            mSummary = (TextView) view.findViewById(R.id.tv_summary);
            mPendingView = (ImageView) view.findViewById(R.id.pending_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFullName.getText() + "'";
        }
    }
}
