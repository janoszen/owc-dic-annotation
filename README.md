# OWC DIC annotation parser

This library provides automatic configuration of the [OWC dependency injector](https://github.com/opsbears/owc-dic) 
based on annotations. It uses [ronmamo's reflections library](https://github.com/ronmamo/reflections) to do that.

The library provides the following classes:

- `AnnotationProcessor` - Parses `@Inject` and `@Singleton` annotations, as well as `Provider` interface implementations for factories for one specified class.
- `AnnotationConfigurator` - Uses the `AnnotationProcessor` to parse a whole package.
- `AutomaticInterfaceImplementationConfigurator` - Automatically configures interface implementations if only one implementation exists.

## Installation

This package can be installed using Maven:

```xml
<dependencies>
    <dependency>
        <groupId>com.opsbears.webcomponents.dic</groupId>
        <artifactId>annnotation</artifactId>
        <version>1.0.0-alpha4</version>
    </dependency>
</dependencies>
```

## Usage

Assuming you have a list of packages from your application you want to configure, you would run the following:

```java
public InjectorConfiguration configure(
    InjectorConfiguration injectorConfiguration
) {
    AnnotationConfigurator annotationConfigurator = new AnnotationConfigurator();
    AutomaticInterfaceImplementationConfigurator interfaceImplementationConfigurator = new AutomaticInterfaceImplementationConfigurator();
    //Configure annotations
    for (Package packageDefinition : packageDefinitions) {
        injectorConfiguration = annotationConfigurator.configure(packageDefinition, injectorConfiguration);
    }
    //Configuire automatic interface implementations
    for (Package packageDefinition : packageDefinitions) {
        injectorConfiguration = interfaceImplementationConfigurator.configure(packageDefinition, injectorConfiguration);
    }

    return injectorConfiguration;
}
```

That's it! You will now have an injection configuration object you can pass to the dependency injector of your choosing,
containing the appropriate configuration.

If you need more information on how to use the dependency injector, [check out the extensive documentation here](https://github.com/opsbears/owc-dic).
