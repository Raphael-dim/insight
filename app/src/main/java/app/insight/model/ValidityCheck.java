package app.insight.model;

import java.util.List;

public class ValidityCheck {
    private List<Integer> solHoursWithData;
    private boolean valid;

    public ValidityCheck(List<Integer> solHoursWithData, boolean valid) {
        this.solHoursWithData = solHoursWithData;
        this.valid = valid;
    }

    public List<Integer> getSolHoursWithData() {
        return solHoursWithData;
    }

    public void setSolHoursWithData(List<Integer> solHoursWithData) {
        this.solHoursWithData = solHoursWithData;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}