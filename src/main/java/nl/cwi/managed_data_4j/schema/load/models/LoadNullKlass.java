package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.implementation.AbstractKlass;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.util.Collections;
import java.util.Set;

public class LoadNullKlass extends AbstractKlass {

    public static final String NAME = "NULL";

    public LoadNullKlass() {
        super(null);
    }

    public LoadNullKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Set<Field> fields(Field...field) {
        return Collections.emptySet();
    }
}