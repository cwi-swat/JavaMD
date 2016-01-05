package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.FieldsField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.SubKlassesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.SupersField;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KlassKlass extends TypeKlass {

    private Schema schema;

    public KlassKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Klass.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {

        final Field supersField = new SupersField(schema, this);
        final Field subKlassesField = new SubKlassesField(schema, this);
        final Field fieldsField = new FieldsField(schema, this);

        // The fields of the klass klass
        Set<Field> klassKlassFields = new HashSet<>(Arrays.asList(
                supersField,
                subKlassesField,
                fieldsField)
        );

        // add the fields of the super class (Type)
        klassKlassFields.addAll(super.fields());

        // return all.
        return klassKlassFields;
    }


    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.singleton(new TypeKlass(schema));
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