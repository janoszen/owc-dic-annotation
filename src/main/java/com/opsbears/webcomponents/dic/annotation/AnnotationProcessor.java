package com.opsbears.webcomponents.dic.annotation;

import com.opsbears.webcomponents.dic.InjectorConfiguration;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class AnnotationProcessor {
    /**
     * Parses @Inject annotations on the specified class constructors ONLY. Method or parameter injection is not
     * supported.
     *
     * @param classList the list of classes to process.
     * @param injectorConfiguration the injector configuration to return a modified copy of.
     *
     * @return a modified copy of the injector configuration.
     */
    public InjectorConfiguration process(Collection<Class> classList, InjectorConfiguration injectorConfiguration) {
        for (Class classDefinition : classList) {
            for (Constructor constructor : classDefinition.getConstructors()) {
                Annotation injectAnnotation = constructor.getAnnotation(Inject.class);
                if (injectAnnotation != null) {
                    if ((classDefinition.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
                        throw new UnsupportedOperationException("Found @Inject annotation on a constructor in non-public class" + classDefinition.getName() + ". This injector only supports public constructors. Alternatively, you can use a factory if needed.");
                    }
                    if ((constructor.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
                        throw new UnsupportedOperationException("Found @Inject annotation on a non-public constructor in " + classDefinition.getName() + ". This injector only supports public constructors.");
                    }
                    injectorConfiguration = injectorConfiguration.withDefined(constructor);
                }
            }

            for (Field field : classDefinition.getFields()) {
                Annotation injectAnnotation = field.getAnnotation(Inject.class);
                if (injectAnnotation != null) {
                    throw new UnsupportedOperationException("Found @Inject annotation on " + classDefinition.getName() + "::" + field.getName() + ". This injector only supports constructor injection.");
                }
            }

            for (Method method : classDefinition.getMethods()) {
                Annotation injectAnnotation = method.getAnnotation(Inject.class);
                if (injectAnnotation != null) {
                    throw new UnsupportedOperationException("Found @Inject annotation on " + classDefinition.getName() + "::" + method.getName() + ". This injector only supports constructor injection.");
                }
            }

            Annotation singleton = classDefinition.getAnnotation(Singleton.class);
            if (singleton != null) {
                if ((classDefinition.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
                    throw new UnsupportedOperationException("Found @Singleton annotation on a constructor in non-public class" + classDefinition.getName() + ". This injector only supports public constructors.");
                }
                injectorConfiguration = injectorConfiguration.withShared(classDefinition);
            }

            if (Provider.class.isAssignableFrom(classDefinition)) {
                for (int i = 0; i < classDefinition.getGenericInterfaces().length; i++) {
                    Type genericInterface = classDefinition.getGenericInterfaces()[i];
                    if (genericInterface instanceof ParameterizedType && ((ParameterizedType)genericInterface).getRawType().equals(Provider.class)) {
                        Type factoryTargetClass = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
                        if (factoryTargetClass instanceof Class){
                            //noinspection unchecked
                            injectorConfiguration = injectorConfiguration.withFactory((Class)factoryTargetClass, classDefinition);
                        }
                    }
                }
            }
        }

        return injectorConfiguration;
    }
}
