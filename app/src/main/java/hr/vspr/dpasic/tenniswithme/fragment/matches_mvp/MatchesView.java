package hr.vspr.dpasic.tenniswithme.fragment.matches_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by edjapas on 24.1.2017..
 */

public interface MatchesView extends RestView {

    void updateListView(List<Match> matches);
}
