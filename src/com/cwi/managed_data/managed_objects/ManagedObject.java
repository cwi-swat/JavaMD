package com.cwi.managed_data.managed_objects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class ManagedObject implements InvocationHandler {

    protected Map<String, Class> types = new HashMap<String, Class>();
    protected Map<String, Object> values = new HashMap<String, Object>();
    protected final Class schema;

    public ManagedObject(Class _schema) {
        this.schema = _schema;

        for (Method field : _schema.getMethods()) {
            saveType(field.getName(), field.getReturnType());
            defaultValue(field.getName(), field.getReturnType());
        }
    }
    
    private void saveType(String _name, Class _type) {
        types.put(_name, _type);
    }
    
    private void defaultValue(String _name, Class _type) {
        // TODO: Better way to do this?
        if (_type == Integer.class) {
            values.put(_name, 0);
        } else if (_type == String.class) {
            values.put(_name, "");
        } else if (_type == Double.class) {
            values.put(_name, 0.0);
        } else {
            values.put(_name, null);
        }
    }

    protected void _set(String _name, Object _value) {
        values.put(_name, _value);
    }

    protected Object _get(String _name) {
        return values.get(_name);
    }

    /**
     * Handle all fields.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        String fieldName = method.getName();
        boolean isAssignment = false;

        // in case there is already the method declared
        // (in one of the sub-classes/sub managedObjects),
        // then invoke it dynamically, and return.
        // * Something like the: create_methods() in Enso-lang
        for (Method declaredMethod : this.getClass().getMethods()) {
            if (declaredMethod.getName().equals(fieldName)) {
                method.invoke(this, args);
                return null;
            }
        }

        Object [] fieldArgs = (Object []) args[0]; // because is varargs

        // TODO: Find a better strategy to decide if it is an assignment.
        // If there is an argument then is considered as assignment.
        if (fieldArgs.length > 0) {
            isAssignment = true;
        }

        // If is assignment
        if (isAssignment) {

            // If there is no such filed.
            if (!types.containsKey(fieldName)) {
                throw new NoSuchFieldError();
            }

            // If the argument is of the right type.
            if (fieldArgs[0].getClass() != values.get(fieldName).getClass()) {
                throw new IllegalArgumentException();
            }

            _set(fieldName, fieldArgs[0]);

        } else { // If is not assignment, return the value.
            return _get(fieldName);
        }

        return null;
    }
}