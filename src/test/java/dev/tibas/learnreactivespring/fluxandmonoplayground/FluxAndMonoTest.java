package dev.tibas.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTestElements_WithoutError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactor Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactor Spring")
                .verifyComplete();

    }

    @Test
    public void fluxTestElements_WithError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactor Spring")
                .concatWith(Flux.error(new RuntimeException("Something Wrong!")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyErrorMessage("Something Wrong!");
    }

    @Test
    public void monoTest() {

        Mono<String> stringMono = Mono.just("Spring")
                .log();

        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error() {

        StepVerifier.create(Mono.error(new RuntimeException("Exception")).log())
                .expectError(RuntimeException.class)
                .verify();
    }

}
