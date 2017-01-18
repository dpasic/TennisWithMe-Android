package hr.vspr.dpasic.tenniswithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.friend_request_mvp.FriendRequestView;
import hr.vspr.dpasic.tenniswithme.model.User;

public class FriendRequestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        FriendRequestView {

    @BindView(R.id.search_people)
    SearchView searchPeople;
    @BindView(R.id.list_people)
    RecyclerView recyclerView;
    @BindView(R.id.activity_friend_request)
    LinearLayout friendRequestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        ButterKnife.bind(this);

        searchPeople.setOnQueryTextListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //TODO: call search people

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void updateListViewAdapter(List<User> users) {

    }

    @Override
    public void notifyRequestError(String msg) {

    }
}
