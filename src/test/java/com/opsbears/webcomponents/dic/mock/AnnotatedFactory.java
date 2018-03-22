package com.opsbears.webcomponents.dic.mock;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Provider;

@ParametersAreNonnullByDefault
public class AnnotatedFactory implements Provider<ConstructorlessClass> {
    @Inject
    public AnnotatedFactory() {

    }

    @Override
    public ConstructorlessClass get() {
        return new ConstructorlessClass();
    }
}
