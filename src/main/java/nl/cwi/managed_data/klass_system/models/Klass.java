package nl.cwi.managed_data.klass_system.models;

import java.util.Set;

public interface Klass extends Type {
    Set<Field> fields(Field ...field);
    Set<Klass> supers(Klass ...sup);

    @Inverse(other=Klass.class, field="supers")
    Set<Klass> subs(Klass ...sub);

    /**
     * The Schema of a KlassImpl is the Schema it belongs to.
     */
    @Inverse(other=Schema.class, field="classes")
    Schema schema(Schema ...schema);

    /**
     * The Interface of which this KlassImpl is made from.
     */
    Class klassInterface(Class ...interfaces);
}