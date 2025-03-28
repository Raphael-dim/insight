package app.insight.model;

import java.io.Serializable;

public class SolData implements Serializable {
    private String solKey;
    private WeatherData temperature;
    private WeatherData pressure;
    private WeatherData windSpeed;
    private String northernSeason;
    private String southernSeason;
    private WindDirection mostCommonWindDirection;
    private String firstUTC;
    private String lastUTC;

    public SolData(String solKey) {
        this.solKey = solKey;
    }

    // Getters and setters
    public String getSolKey() {
        return solKey;
    }

    public void setSolKey(String solKey) {
        this.solKey = solKey;
    }

    public WeatherData getTemperature() {
        return temperature;
    }

    public void setTemperature(WeatherData temperature) {
        this.temperature = temperature;
    }

    public WeatherData getPressure() {
        return pressure;
    }

    public void setPressure(WeatherData pressure) {
        this.pressure = pressure;
    }

    public WeatherData getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WeatherData windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getNorthernSeason() {
        return northernSeason;
    }

    public void setNorthernSeason(String northernSeason) {
        this.northernSeason = northernSeason;
    }

    public String getSouthernSeason() {
        return southernSeason;
    }

    public void setSouthernSeason(String southernSeason) {
        this.southernSeason = southernSeason;
    }

    public WindDirection getMostCommonWindDirection() {
        return mostCommonWindDirection;
    }

    public void setMostCommonWindDirection(WindDirection mostCommonWindDirection) {
        this.mostCommonWindDirection = mostCommonWindDirection;
    }

    public String getFirstUTC() {
        return firstUTC;
    }

    public void setFirstUTC(String firstUTC) {
        this.firstUTC = firstUTC;
    }

    public String getLastUTC() {
        return lastUTC;
    }

    public void setLastUTC(String lastUTC) {
        this.lastUTC = lastUTC;
    }

    public static class WeatherData implements Serializable {
        private static final long serialVersionUID = 1L;
        private double average;
        private double min;
        private double max;

        public WeatherData(double average, double min, double max) {
            this.average = average;
            this.min = min;
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }

    public static class WindDirection implements Serializable {
        private static final long serialVersionUID = 1L;
        private double compassDegrees;
        private String compassPoint;

        public WindDirection(double compassDegrees, String compassPoint) {
            this.compassDegrees = compassDegrees;
            this.compassPoint = compassPoint;
        }

        public double getCompassDegrees() {
            return compassDegrees;
        }

        public String getCompassPoint() {
            return compassPoint;
        }
    }
}
