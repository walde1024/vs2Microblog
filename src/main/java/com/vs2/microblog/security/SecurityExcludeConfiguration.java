package com.vs2.microblog.security;

import org.springframework.stereotype.Component;
import sun.misc.Regexp;

import java.util.List;

/**
 * Created by Walde on 28.03.16.
 */
@Component
public class SecurityExcludeConfiguration {

    List<String> excludedPaths;

    public SecurityExcludeConfiguration(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public boolean isExcluded(String path) {
        for(String excludedPath: excludedPaths) {
            if (path.indexOf(excludedPath) == 0) {
                return true;
            }
        }

        return false;
    }
}
