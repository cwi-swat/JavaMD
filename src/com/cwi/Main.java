package com.cwi;

import com.cwi.managed_data.examples.schemas.Point;
import com.cwi.managed_data.klass_system.factories.Factory;
import com.cwi.managed_data.klass_system.helpers.SchemaManager;
import com.cwi.managed_data.klass_system.models.Schema;
import com.cwi.managed_data.managed_objects.factories.ContractorFactory;
import com.cwi.managed_data.managed_objects.factories.InitializationFactory;
import com.cwi.managed_data.managed_objects.factories.ObservableFactory;
import com.cwi.managed_data.roles.contract.ContractBrokenException;
import com.cwi.managed_data.roles.contract.Contractor;
import com.cwi.managed_data.roles.observer.Observable;

public class Main {

    public static void main(String[] args) throws Throwable {

        SchemaManager schemaManager = new SchemaManager();

        Schema pointSchema = schemaManager.make(Point.class);

        System.out.println("\nBasic Record Point: ");
        Factory pointFactory = new Factory(pointSchema);
        Point basicPoint = pointFactory.make();
        basicPoint.x(3);
        basicPoint.y(7);
        System.out.println("\t " + (basicPoint.x() + basicPoint.y()));

        // ================================================================================

        System.out.println("\nInit Record Point: ");
        Factory initPointFactory = new InitializationFactory(pointSchema);

        Point initPoint = initPointFactory.make(10, 22);
        System.out.println("\t " + (initPoint.x() + initPoint.y()));

        try {
            initPoint.x(44);
        } catch (IllegalAccessError e) {
            System.out.println("\t " + e.getMessage());
        }

        // ==================================================================================

        System.out.println("\nObserver Record Point: ");
        Factory observerPointFactory = new ObservableFactory(pointSchema);

        // Create a new observer-record managed object.
        Point observerPoint = observerPointFactory.make();

        ((Observable) observerPoint)
            .observe((thisObject, field, value) ->
                    System.out.println("\t Updated " + field + " to " + value));

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.x(observerPoint.x() + observerPoint.y());

        // ==================================================================================

        System.out.println("\nContractor Record Point: ");
        Factory contractorPointFactory = new ContractorFactory(pointSchema);

        // Create a new observer-record managed object.
        Point contractorPoint = contractorPointFactory.make();

        // Add precondition
        ((Contractor) contractorPoint)
            .require((thisObject, field, value) -> {
                if (((Integer) value) == -1) {
                    throw new ContractBrokenException("precondition violated");
                }
            });

        // Add postcondition
        ((Contractor) contractorPoint)
            .ensure((thisObject, field, value) -> {
                if (((Integer) value) == -1) {
                    throw new ContractBrokenException("postcondition violated");
                }
            });

        contractorPoint.y(1);
        System.out.println("\t" + contractorPoint.y());
        contractorPoint.x(-1);
        System.out.println("\t" + contractorPoint.x());
    }
}