package dev.tibas.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    String[] names = new String[]{"Alysson", "Tibério", "Dulci", "Sergi", "Ana"};

    @Test
    public void filterTest() {

        Flux<String> namesFlux = Flux.fromArray(names)
                .filter(name -> name.startsWith("A"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Alysson", "Ana")
                .verifyComplete();
    }

    @Test
    public void filterTestLenght() {

        Flux<String> namesFlux = Flux.fromArray(names)
                .filter(name -> name.length() > 5)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Alysson", "Tibério")
                .verifyComplete();
    }

}
