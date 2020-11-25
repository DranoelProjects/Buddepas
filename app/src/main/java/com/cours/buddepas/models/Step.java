package com.cours.buddepas.models;

public class Step {
    private Integer stepOrder;
    private String description;

    public Step(Integer stepOrder, String description) {
        this.stepOrder = stepOrder;
        this.description = description;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
