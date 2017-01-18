package hr.vspr.dpasic.tenniswithme.common;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;

/**
 * Created by dpasic on 1/18/17.
 */

public interface RestSubscriber {

    void doRequest(RestPublisher publisher, AccessToken token);
}
