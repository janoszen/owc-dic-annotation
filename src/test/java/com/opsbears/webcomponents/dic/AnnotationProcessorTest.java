package com.opsbears.webcomponents.dic;

import com.opsbears.webcomponents.dic.annotation.AnnotationProcessor;
import com.opsbears.webcomponents.dic.jit.JITInjector;
import com.opsbears.webcomponents.dic.mock.AnnotatedFactory;
import com.opsbears.webcomponents.dic.mock.AnnotatedInjectable;
import com.opsbears.webcomponents.dic.mock.ConstructorlessClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AnnotationProcessorTest {
    @Test
    public void testAnnotations() {
        InjectorConfiguration config = new InjectorConfiguration();
        AnnotationProcessor annotationParser = new AnnotationProcessor();
        config = annotationParser.process(Arrays.asList(
            AnnotatedInjectable.class,
            AnnotatedFactory.class
        ), config);
        config = config.withNamedParameterValue(AnnotatedInjectable.class, "test", "Hello world!");
        Injector dic = new JITInjector(
            config
        );
        AnnotatedInjectable annotatedInjectableInstance = dic.make(AnnotatedInjectable.class);
        assertEquals("Hello world!", annotatedInjectableInstance.getTest());

        ConstructorlessClass constructorlessClassInstance =  dic.make(ConstructorlessClass.class);
        assertNotNull(constructorlessClassInstance);
    }
}
