package com.medipath.labcare.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseTestDetails {

    // 👇 Now mandatory for all test-type entities to override
    public abstract String getCost();

    public abstract String getNormalRange();

    public abstract String getUnit();
}
