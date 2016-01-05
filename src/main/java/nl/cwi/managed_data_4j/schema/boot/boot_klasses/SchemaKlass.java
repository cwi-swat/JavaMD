package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.KlassInterfacesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.KlassesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.PrimitivesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.TypesField;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchemaKlass implements Klass {

    private Schema schema;

    public SchemaKlass(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return Schema.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {
        final Field typesField = new TypesField(schema, this);
        final Field klassInterfacesField = new KlassInterfacesField(schema, this);
        final Field primitivesField = new PrimitivesField(schema, this);
        final Field klassesField = new KlassesField(schema, this);

        return new HashSet<>(Arrays.asList(
            typesField,
            klassInterfacesField,
            primitivesField,
            klassesField
        ));
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.emptySet();
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return Collections.emptySet();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}