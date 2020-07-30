package dev.tibas.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {

    String[] names = new String[]{"Alysson", "Tibério", "Dulci", "Sergi"};

    @Test
    public void transformUsingMap() {

        Flux<String> namesFlux = Flux.fromArray(names)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("ALYSSON", "TIBÉRIO", "DULCI", "SERGI")
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Length() {

        Flux<Integer> namesFlux = Flux.fromArray(names)
                .map(String::length)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(7, 7, 5, 5)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Length_repeat() {

        Flux<Integer> namesFlux = Flux.fromArray(names)
                .map(String::length)
                .repeat(1)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(7, 7, 5, 5, 7, 7, 5, 5)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Filter() {

        Flux<String> namesFlux = Flux.fromArray(names)
                .filter(name -> name.length() > 5)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("ALYSSON", "TIBÉRIO")
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMapp() {

        Flux<String> lettersFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMapSequential(letraFlux -> letraFlux.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(Flux::fromIterable)
                .log();

        StepVerifier.create(lettersFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> convertToList(String value) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(value, "newValue");
    }
}
