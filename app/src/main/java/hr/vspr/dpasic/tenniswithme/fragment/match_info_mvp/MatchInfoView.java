package hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Match;

/**
 * Created by edjapas on 30.1.2017..
 */

public interface MatchInfoView extends RestView {

    void acceptedMatch();
    void updateMatch(Match match);
}
