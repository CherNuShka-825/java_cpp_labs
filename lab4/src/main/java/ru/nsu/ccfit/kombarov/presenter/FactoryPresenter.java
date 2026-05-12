package ru.nsu.ccfit.kombarov.presenter;

import javafx.animation.AnimationTimer;
import ru.nsu.ccfit.kombarov.model.factory.Factory;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryDelays;
import ru.nsu.ccfit.kombarov.view.FactoryView;

public final class FactoryPresenter {

    private final Factory model;
    private final FactoryView view;

    public FactoryPresenter(Factory model, FactoryView view) {
        this.model = model;
        this.view = view;

        initSliderBindings();
        startUpdateLoop();
    }

    private void initSliderBindings() {
        view.getBodyDelaySlider().valueProperty().addListener((obs, old, val) -> {
            FactoryDelays.setBodySupplierDelayMs(val.intValue());
            view.updateDelayLabels();
        });

        view.getMotorDelaySlider().valueProperty().addListener((obs, old, val) -> {
            FactoryDelays.setMotorSupplierDelayMs(val.intValue());
            view.updateDelayLabels();
        });

        view.getAccessoryDelaySlider().valueProperty().addListener((obs, old, val) -> {
            FactoryDelays.setAccessorySupplierDelayMs(val.intValue());
            view.updateDelayLabels();
        });

        view.getDealerDelaySlider().valueProperty().addListener((obs, old, val) -> {
            FactoryDelays.setDealerDelayMs(val.intValue());
            view.updateDelayLabels();
        });

        view.getWorkerDelaySlider().valueProperty().addListener((obs, old, val) -> {
            FactoryDelays.setWorkerDelayMs(val.intValue());
            view.updateDelayLabels();
        });

        view.getBodyDelaySlider().setValue(FactoryDelays.getBodySupplierDelayMs());
        view.getMotorDelaySlider().setValue(FactoryDelays.getMotorSupplierDelayMs());
        view.getAccessoryDelaySlider().setValue(FactoryDelays.getAccessorySupplierDelayMs());
        view.getDealerDelaySlider().setValue(FactoryDelays.getDealerDelayMs());
        view.getWorkerDelaySlider().setValue(FactoryDelays.getWorkerDelayMs());

        view.updateDelayLabels();
    }

    private void startUpdateLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                view.setBodyStorage(
                        model.getBodyStorage().getSize(),
                        model.getBodyStorage().getCapacity()
                );

                view.setMotorStorage(
                        model.getMotorStorage().getSize(),
                        model.getMotorStorage().getCapacity()
                );

                view.setAccessoryStorage(
                        model.getAccessoryStorage().getSize(),
                        model.getAccessoryStorage().getCapacity()
                );

                view.setAutoStorage(
                        model.getAutoStorage().getSize(),
                        model.getAutoStorage().getCapacity()
                );

                view.setStatistics(
                        (int) model.getStatistics().getSoldAutos(),
                        model.getThreadPool().getQueueSize()
                );
            }
        }.start();
    }
}