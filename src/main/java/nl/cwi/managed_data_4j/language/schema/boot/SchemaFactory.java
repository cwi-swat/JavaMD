package nl.cwi.managed_data_4j.language.schema.boot;

import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

/**
 * A factory for schema klasses
 * @author Theologos Zacharopoulos
 */
public interface SchemaFactory {

    /**
     * Builds a new empty Schema
     */
    Schema Schema();

    /**
     * Builds a new Primitive with its type name
     */
    Primitive Primitive();

    /**
     * Builds a new empty Klass
     */
    Klass Klass();

    /**
     * Builds a new empty Field
     */
    Field Field();

    /**
     * Builds a new Field with given the following arguments.
     * Consider that those arguments are given with alphabetical order which is
     * used as the default order standard for initialization in this framework.
     */
    Field Field(Boolean contain, Boolean key, Boolean many, String name, Boolean optional);
}
