package com.owen.imageloader.policy;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class ReversePolicy implements LoadPolicy {

    @Override
    public int compare(Target request1, Target request2) {
        return request2.sequenceNumber - request1.sequenceNumber;
    }
}
