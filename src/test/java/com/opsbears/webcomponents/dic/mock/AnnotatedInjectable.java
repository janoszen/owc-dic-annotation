package com.opsbears.webcomponents.dic.mock;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Named;

@ParametersAreNonnullByDefault
public class AnnotatedInjectable {
    private final String test;

    @Inject
    public AnnotatedInjectable(@Named("test") String x) {
        this.test = x;
    }

    public String getTest() {
        return test;
    }
}
