package com.example.test.service.impl;

import com.example.test.model.Point;
import com.example.test.repository.PointRepository;
import com.example.test.service.PointService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class PointServiceImpl implements PointService {

    private final PointRepository repository;

    @Autowired
    public PointServiceImpl(PointRepository repository) {
        this.repository = repository;
    }

    @Override
    public Point getPoint() {
        if (!existsAny()) {
            return createPoint();
        }

        return repository.findById(1L).orElseThrow();
    }

    @Override
    public Point createPoint() {
        return repository.save(new Point("0"));
    }

    @Override
    public Point save(Point point) {
        return repository.save(point);
    }

    @Override
    public void incPoints(Point point) {
        try {
            long value = Long.parseLong(point.getPoints());
            point.setPoints(String.valueOf(value + 1L));
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Boolean existsAny() {
        return repository.count() != 0;
    }
}
