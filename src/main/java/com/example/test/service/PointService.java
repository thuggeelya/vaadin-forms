package com.example.test.service;

import com.example.test.model.Point;

public interface PointService {

    Point getPoint();

    Point createPoint();

    Point save(Point point);

    void incPoints(Point point);

    Boolean existsAny();
}
