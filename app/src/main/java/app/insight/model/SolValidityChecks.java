package app.insight.model;

public class SolValidityChecks {
    private ValidityCheck at; // Atmospheric Temperature
    private ValidityCheck hws; // Horizontal Wind Speed
    private ValidityCheck pre; // Pressure
    private ValidityCheck wd; // Wind Direction

    public SolValidityChecks(ValidityCheck at, ValidityCheck hws, ValidityCheck pre, ValidityCheck wd) {
        this.at = at;
        this.hws = hws;
        this.pre = pre;
        this.wd = wd;
    }

    public ValidityCheck getAt() {
        return at;
    }

    public void setAt(ValidityCheck at) {
        this.at = at;
    }

    public ValidityCheck getHws() {
        return hws;
    }

    public void setHws(ValidityCheck hws) {
        this.hws = hws;
    }

    public ValidityCheck getPre() {
        return pre;
    }

    public void setPre(ValidityCheck pre) {
        this.pre = pre;
    }

    public ValidityCheck getWd() {
        return wd;
    }

    public void setWd(ValidityCheck wd) {
        this.wd = wd;
    }
}