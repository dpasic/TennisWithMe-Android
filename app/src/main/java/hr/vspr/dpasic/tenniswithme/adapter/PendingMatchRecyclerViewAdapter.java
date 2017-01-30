package hr.vspr.dpasic.tenniswithme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnMatchListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Match;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Match} and makes a call to the
 * specified {@link OnMatchListFragmentInteractionListener}.
 *
 */
public class PendingMatchRecyclerViewAdapter extends RecyclerView.Adapter<PendingMatchRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final List<Match> mValues;
    private final ActionType mActionType;
    private final OnMatchListFragmentInteractionListener mListener;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    public PendingMatchRecyclerViewAdapter(Context context, List<Match> items, ActionType actionType, OnMatchListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mActionType = actionType;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        final boolean isMatchReceived = holder.mItem.isMatchReceived();
        String vsFullName;

        if (isMatchReceived) {
            holder.mPendingView.setImageResource(R.mipmap.confirm_match);
            vsFullName = String.format("%s %s", mContext.getString(R.string.vs), holder.mItem.getPlayerOneName());
        } else {
            holder.mPendingView.setImageResource(R.mipmap.sent_request);
            vsFullName = String.format("%s %s", mContext.getString(R.string.vs), holder.mItem.getPlayerTwoName());
        }

        holder.mVsFullName.setText(vsFullName);
        holder.mDate.setText(sdf.format(holder.mItem.getDatePlayed()));
        holder.mCity.setText(holder.mItem.getCityPlayed());
        holder.mComment.setText(holder.mItem.getComment());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (isMatchReceived) {
                        mListener.onListFragmentInteraction(holder.mItem, ActionType.CONFIRM_MATCH);
                    } else {
                        mListener.onListFragmentInteraction(holder.mItem, ActionType.VIEW_AND_EDIT);
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

        public Match mItem;

        public final View mView;
        public final TextView mVsFullName;
        public final TextView mDate;
        public final TextView mCity;
        public final TextView mComment;
        public final ImageView mPendingView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mVsFullName = (TextView) view.findViewById(R.id.tv_vs_full_name);
            mDate = (TextView) view.findViewById(R.id.tv_date);
            mCity = (TextView) view.findViewById(R.id.tv_city);
            mComment = (TextView) view.findViewById(R.id.tv_comment);
            mPendingView = (ImageView) view.findViewById(R.id.pending_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCity.getText() + "'";
        }
    }
}
