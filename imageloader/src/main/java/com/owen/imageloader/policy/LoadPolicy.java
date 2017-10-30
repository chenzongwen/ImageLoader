package com.owen.imageloader.policy;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public interface LoadPolicy {
    public int compare(Target request1, Target request2);
}
