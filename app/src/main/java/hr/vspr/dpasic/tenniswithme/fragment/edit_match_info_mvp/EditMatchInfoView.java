package hr.vspr.dpasic.tenniswithme.fragment.edit_match_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Match;

/**
 * Created by edjapas on 30.1.2017..
 */

public interface EditMatchInfoView extends RestView {

    void onUpdatedMatch(Match match);
}
