package com.example.test;

import com.example.test.model.Point;
import com.example.test.service.PointService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j;
import org.springframework.transaction.annotation.Transactional;

@Route
@Log4j
public class MainView extends VerticalLayout {

    private final PointService service;
    private final Point currentPoint;
    private final TextField textField;
    private final Button button;

    public MainView(PointService service) {
        this.service = service;
        currentPoint = service.getPoint();
        textField = initTextField();
        button = initButton();
        log.debug(String.format("TextField and Button have been initialized for %s", currentPoint.toString()));
        add(textField);
        add(button);
    }

    private Button initButton() {
        final Button button = new Button("Increment");
        button.addClickListener((e) -> {
            if (!textField.isInvalid()) {
                incPointsAndSave();
            }
        });
        return button;
    }

    private TextField initTextField() {
        final TextField textField = new TextField();
        textField.setPattern("^-{0,1}[0-9]+$");
        textField.setAllowedCharPattern("[-0-9]");
        textField.setErrorMessage("Number is incorrect or too large to increment");
        textField.setRequired(true);
        textField.setValue(currentPoint.getPoints());
        textField.setMinLength(1);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener((e) -> {
            if (!textField.isInvalid()) {
                setPointsAndSave();
            }
        });
        return textField;
    }

    @Transactional
    void setPointsAndSave() {
        try {
            String longValueWithoutLeadingZeros = String.valueOf(Long.parseLong(textField.getValue()));
            currentPoint.setPoints(longValueWithoutLeadingZeros);
            textField.setValue(longValueWithoutLeadingZeros);
            service.save(currentPoint);
        } catch (NumberFormatException e) {
            textField.setInvalid(true);
            log.error(e.getMessage());
        }
    }

    @Transactional
    void incPointsAndSave() {
        String value = textField.getValue();

        if (!value.equals(currentPoint.getPoints())) {
            currentPoint.setPoints(value);
        }

        service.incPoints(currentPoint);
        textField.setValue(currentPoint.getPoints());
        service.save(currentPoint);
    }
}
