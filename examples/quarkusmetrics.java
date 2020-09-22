///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.quarkus:quarkus-resteasy:1.8.1.Final
//DEPS io.quarkus:quarkus-smallrye-metrics:1.8.1.Final
//JAVAC_OPTIONS -parameters
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import io.quarkus.runtime.Quarkus;

@Path("/")
public class quarkusmetrics {

    private long highestPrimeNumberSoFar = 2;

    @GET
    @Path("/{number}")
    @Produces("text/plain")
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkIfPrime(@PathParam long number) {
        if (number < 1) {



            return "Only natural numbers can b  e prime numbers.";
        }
        if (number == 1) {
            return "1 is not prime.";
        }
        if (number == 2) {
                    return "2 is prime.";
        }
        if (number % 2 == 0) {
            return number + " is not prime, it is divisible by 2.";
        }
        for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
            if (number % i == 0) {
                return number + " is not prime,    is divisible by " + i + ".";
            }
        }
        if (number > highestPrimeNumberSoFar) {
            highestPrimeNumberSoFar = number;
        }
        return number + " is prime.";
    }

    @Gauge(name = "highestPrimeNumberSoFar", unit = MetricUnits.NONE, description = "Highest prime number so far.")
    public Long highestPrimeNumberSoFar() {
        return highestPrimeNumberSoFar;
    }

    public static void main(String... args) {
        Quarkus.run(args);
    }

}