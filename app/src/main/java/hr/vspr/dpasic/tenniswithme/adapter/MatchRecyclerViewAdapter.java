package hr.vspr.dpasic.tenniswithme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnMatchListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Match} and makes a call to the
 * specified {@link OnMatchListFragmentInteractionListener}.
 *
 */
public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final List<Match> mValues;
    private final ActionType mActionType;
    private final OnMatchListFragmentInteractionListener mListener;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    public MatchRecyclerViewAdapter(Context context, List<Match> items, ActionType actionType, OnMatchListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mActionType = actionType;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String vsFullName = String.format("%s %s", mContext.getString(R.string.vs), mValues.get(position).getPlayerTwoName());
        holder.mVsFullName.setText(vsFullName);
        holder.mDate.setText(sdf.format(mValues.get(position).getDatePlayed()));
        holder.mCity.setText(mValues.get(position).getCityPlayed());
        holder.mComment.setText(mValues.get(position).getComment());

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

        public Match mItem;

        public final View mView;
        public final TextView mVsFullName;
        public final TextView mDate;
        public final TextView mCity;
        public final TextView mComment;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mVsFullName = (TextView) view.findViewById(R.id.tv_vs_full_name);
            mDate = (TextView) view.findViewById(R.id.tv_date);
            mCity = (TextView) view.findViewById(R.id.tv_city);
            mComment = (TextView) view.findViewById(R.id.tv_comment);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCity.getText() + "'";
        }
    }
}
