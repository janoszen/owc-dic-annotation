package com.opsbears.webcomponents.dic.annotation;

import com.opsbears.webcomponents.dic.InjectorConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class AnnotationConfigurator {
    public InjectorConfiguration configure(Package packageDefinition, InjectorConfiguration injectorConfiguration) {
        Reflections reflections = new Reflections(packageDefinition.getName(), new SubTypesScanner(false));
        Collection<Class> classes = new ArrayList<>(reflections.getSubTypesOf(Object.class));
        AnnotationProcessor annotationProcessor = new AnnotationProcessor();

        return annotationProcessor.process(
            classes,
            injectorConfiguration
        );
    }
}
