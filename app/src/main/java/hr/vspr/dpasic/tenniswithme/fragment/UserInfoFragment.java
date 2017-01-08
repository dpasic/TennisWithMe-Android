package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.EditUserInfoActivity;
import hr.vspr.dpasic.tenniswithme.edit_user_info_mvp.UserInfoPublisher;
import hr.vspr.dpasic.tenniswithme.edit_user_info_mvp.UserInfoSubscriber;
import hr.vspr.dpasic.tenniswithme.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment implements UserInfoSubscriber, Serializable {

    private static final String USER = "user";

    private User user;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_summary)
    TextView tvSummary;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInfoFragment.
     */
    public static UserInfoFragment newInstance(User user) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);

        updateUserInfo();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: read user info from REST
    }

    private void updateUserInfo() {
        tvFullName.setText(user.getFullName());
        tvEmail.setText(user.getEmail());
        tvAge.setText(user.getAge());
        tvSex.setText(user.getSex());
        tvSummary.setText(user.getSummary());
    }

    @OnClick(R.id.fab_edit)
    public void editUserInfoClick() {
        Intent editUserInfoActivity = new Intent(getContext(), EditUserInfoActivity.class);
        editUserInfoActivity.putExtra(USER, user);

        startActivity(editUserInfoActivity);
    }

    @Override
    public void update(UserInfoPublisher publisher, User user) {
        this.user = user;
        updateUserInfo();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
