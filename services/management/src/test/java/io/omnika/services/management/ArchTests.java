package io.omnika.services.management;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;
import org.springframework.stereotype.Service;

@AnalyzeClasses(packagesOf = OmnikaManagementApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchTests {

    private final DescribedPredicate<JavaClass> serviceInterfacePredicate = new DescribedPredicate<>("interface") {
        @Override
        public boolean apply(JavaClass input) {
            return input.isInterface() && input.getPackageName().contains(".core.service");
        }
    };

    @ArchTest
    void testMapperAccessedOnlyFromConverter(JavaClasses javaClasses) {
        Architectures.layeredArchitecture()
                .layer("Mappers").definedBy("..management.converters.mappers..")
                .layer("Converters").definedBy("..management.converters..")

                .whereLayer("Mappers").mayOnlyBeAccessedByLayers("Converters")

                .check(javaClasses);
    }

    @ArchTest
    void testLayeredArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .layer("Web").definedBy("..web..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")

                .whereLayer("Web").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")

                .check(classes);
    }

    @ArchTest
    void serviceShouldImplementInterfaceSpecifiedInCore(JavaClasses javaClasses) {
        classes()
                .that().areAnnotatedWith(Service.class)
                .should().implement(serviceInterfacePredicate)
                .check(javaClasses);
    }

    @ArchTest
    void serviceImplShouldBePackagePrivate(JavaClasses classes) {
        classes()
                .that().implement(serviceInterfacePredicate).and().doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().bePackagePrivate()
                .check(classes);
    }

}
