package hr.vspr.dpasic.tenniswithme.activity.request_match_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Match;

/**
 * Created by edjapas on 24.1.2017..
 */

public interface RequestMatchView extends RestView {

    void requestCompleted(Match match);
}
