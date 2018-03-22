package com.opsbears.webcomponents.dic.annotation;

import com.opsbears.webcomponents.dic.InjectorConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@ParametersAreNonnullByDefault
public class AutomaticInterfaceImplementationConfigurator {
    public InjectorConfiguration configure(Package packageDefinition, InjectorConfiguration injectorConfiguration) {
        Reflections reflections = new Reflections(packageDefinition.getName(), new SubTypesScanner(false));
        Collection<Class> classes = new ArrayList<>(reflections.getSubTypesOf(Object.class));

        for (Class classDefinition : classes) {
            if (!classDefinition.isInterface()) {
                Class[] interfaces = classDefinition.getInterfaces();
                for (Class interfaceDefinition : interfaces) {
                    //noinspection unchecked
                    Set<Class<?>> subTypes = reflections.getSubTypesOf(interfaceDefinition);
                    if (subTypes.size() == 1) {
                        //noinspection unchecked
                        injectorConfiguration = injectorConfiguration.withAlias(interfaceDefinition, classDefinition);
                    }
                }
            }
        }

        return injectorConfiguration;
    }
}
