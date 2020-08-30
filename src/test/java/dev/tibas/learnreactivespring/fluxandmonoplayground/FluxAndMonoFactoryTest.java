package dev.tibas.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    String[] names = new String[]{"Alysson", "Tibério", "Dulci", "Sergi"};

    @Test
    public void fluxUsingArray() {

        Flux<String> namesFlux = Flux.fromArray(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void fluxUsingIterable() {

        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList(names))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Alysson", "Tibério", "Dulci", "Sergi")
                .verifyComplete();

    }

    @Test
    public void fluxUsingStream() {

        Flux<String> namesFlux = Flux.fromStream(Arrays.stream(names))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Alysson", "Tibério", "Dulci", "Sergi")
                .verifyComplete();

    }

    @Test
    public void monoUsingJustOrEmpty() {

        Mono<String> mono = Mono.justOrEmpty(Optional.empty()); //Mono.empty();

        StepVerifier.create(mono.log())
                .verifyComplete();
    }


    @Test
    public void monoUsingSupplier() {

        Supplier<String> stringSupplier = () -> "adam";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier).log();

        StepVerifier.create(stringMono)
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {

        Flux<Integer> integerFlux = Flux.range(1, 5)
                .log();

        StepVerifier.create(integerFlux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

        StepVerifier.create(integerFlux)
                .expectNextCount(5)
                .verifyComplete();
    }


}

