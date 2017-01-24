package hr.vspr.dpasic.tenniswithme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Player} and makes a call to the
 * specified {@link OnPeopleListFragmentInteractionListener}.
 *
 */
public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder> {

    private final List<Player> mValues;
    private final ActionType mActionType;
    private final OnPeopleListFragmentInteractionListener mListener;

    public PlayerRecyclerViewAdapter(List<Player> items, ActionType actionType, OnPeopleListFragmentInteractionListener listener) {
        mValues = items;
        mActionType = actionType;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mFullName.setText(mValues.get(position).getFullName());
        holder.mSkill.setText(mValues.get(position).getSkill());
        holder.mGender.setText(mValues.get(position).getGender());
        holder.mSummary.setText(mValues.get(position).getSummary());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, mActionType);
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFullName = (TextView) view.findViewById(R.id.tv_full_name);
            mSkill = (TextView) view.findViewById(R.id.tv_skill);
            mGender = (TextView) view.findViewById(R.id.tv_gender);
            mSummary = (TextView) view.findViewById(R.id.tv_summary);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFullName.getText() + "'";
        }
    }
}
