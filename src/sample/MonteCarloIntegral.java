package sample;

import javafx.scene.chart.XYChart;

import java.util.Random;

public class MonteCarloIntegral implements Runnable
{
    private final double BOLTZMANN_K = 1.38064E-23;
    private XYChart.Series data;
    private double temperature, mass, vmin, vmax;
    private int numberOfPoints;
    private Random random;

    public MonteCarloIntegral(XYChart.Series data, double mass, double temperature, int numberOfPoints, double vmin, double vmax, Random random)
    {
        this.data = data;
        this.mass = mass;
        this.temperature = temperature;
        this.numberOfPoints = numberOfPoints;
        this.vmin = vmin;
        this.vmax = vmax;
        this.random = random;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        double gi, G;

        // convert mass from au to kg
        mass *= 1.66053904E-27;

        double extraTerm = (Math.PI / 8.0) * Math.pow((mass / (BOLTZMANN_K * temperature)), 3.0);


    }
}
