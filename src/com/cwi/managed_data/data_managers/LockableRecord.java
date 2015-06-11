package com.cwi.managed_data.data_managers;

public class LockableRecord extends BasicRecord {

    private boolean isLocked = false;

    public LockableRecord(Class _schema) {
        super(_schema);
    }

    protected void lock() {
        isLocked = true;
    }

    @Override
    protected void _set(String _name, Object _value) {
        if (isLocked) {
          throw new IllegalAccessError("Cannot change " + _name + " of locked object");
        }
        super._set(_name, _value);
    }
}