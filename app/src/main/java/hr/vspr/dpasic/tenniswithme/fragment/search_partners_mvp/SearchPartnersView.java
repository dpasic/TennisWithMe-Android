package hr.vspr.dpasic.tenniswithme.fragment.search_partners_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by edjapas on 20.1.2017..
 */

public interface SearchPartnersView extends RestView {

    void updateListViewAdapter(List<Player> players);
}
