package sample;

import javafx.scene.chart.XYChart;

import java.util.Random;

public class MaxwellBoltzmann
{
    private final double BOLTZMANN_K = 1.38064E-23;
    private Random random;
    private double integralValue = 0.0;

    public MaxwellBoltzmann()
    {
        random = new Random();
    }

    public XYChart.Series getVelocityDistribution(double mass, double temperature, double vmin, double vmax, int numberOfPoints)
    {
        XYChart.Series data = new XYChart.Series();
        double f, dv, v;

        // convert mass from au to kg
        mass *= 1.66053904E-27;

        dv = (vmax - vmin) / numberOfPoints;
        v = vmin;

        for (int i = 0; i < numberOfPoints; i++)
        {
            f = mbd(mass, temperature, v);

            data.getData().add(new XYChart.Data<>(v, f));

            v += dv;
        }

        return data;

    }

    public XYChart.Series getVelocityTermDistribution(double mass, double temperature, double vmin, double vmax, int numberOfPoints)
    {
        XYChart.Series data = new XYChart.Series();
        double f, dv, v;

        // convert mass from au to kg
        mass *= 1.66053904E-27;

        dv = (vmax - vmin) / numberOfPoints;
        v = vmin;

        for (int i = 0; i < numberOfPoints; i++)
        {
            f = velocity(mass, temperature, v);

            data.getData().add(new XYChart.Data<>(v, f));

            v += dv;
        }

        return data;
    }

    public XYChart.Series getMCValue(double mass, double temperature, double vmin, double vmax, int numberOfPoints)
    {
        XYChart.Series data = new XYChart.Series();
        double gi, G;

        // convert mass from au to kg
        mass *= 1.66053904E-27;

        double extraTerm = (Math.PI / 8.0) * Math.pow((mass / (BOLTZMANN_K * temperature)), 3.0);

        G = 0;
        for (int i = 0; i < numberOfPoints; i++)
        {
            gi = vmin + random.nextDouble() * (vmax - vmin);
            G += monteCarloIntegral(mass, temperature, gi);
            integralValue = (extraTerm)*(vmax-vmin)*G/(i+1);
//            System.out.println(i+1 + "   " + gi + "   " + integralValue);
            data.getData().add(new XYChart.Data<>(i+1, integralValue));
        }

        return data;
    }

    public double getMCIntegralValue()
    {
        return integralValue;
    }

    private double monteCarloIntegral(double mass, double temperature, double velocity)
    {
        return velocity(mass, temperature, velocity) / mbd(mass, temperature, velocity);
    }

    private double mbd(double mass, double temperature, double velocity)
    {
        return 4.0 * Math.PI * Math.pow(velocity, 2.0) * Math.pow((mass / (2.0 * Math.PI * BOLTZMANN_K * temperature)), 1.5) *
                Math.exp(-(mass * Math.pow(velocity, 2.0)) / (2.0 * BOLTZMANN_K * temperature));

    }

    private double velocity(double mass, double temperature, double velocity)
    {
        double vTerm;

        vTerm = Math.exp(-(mass * velocity * velocity) / (2.0 * BOLTZMANN_K * temperature)) * Math.pow(velocity, 5.0);

//        System.out.println("Mass        = " + mass + " kg");
//        System.out.println("Velocity    = " + velocity + " m/s");
//        System.out.println("Temperature = " +  temperature + " K");
//        System.out.println("VTerm       = " + vTerm);

        return vTerm;
    }




}
