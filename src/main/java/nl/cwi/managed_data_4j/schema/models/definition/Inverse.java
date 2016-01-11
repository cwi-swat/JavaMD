package nl.cwi.managed_data_4j.schema.models.definition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Inverse {
    Class other();
    String field();
}