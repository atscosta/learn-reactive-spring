package dev.tibas.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackpressureTest {

    @Test
    public void backPressureTest() {

        Flux<Integer> finitFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(finitFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressure() {

        Flux<Integer> finitFlux = Flux.range(1, 10)
                .log();

        finitFlux.subscribe(element -> System.out.println("The element is " + element),
                System.err::println,
                () -> System.out.println("Done"),
                subscription -> subscription.request(2));
    }

    @Test
    public void backPressure_cancel() {

        Flux<Integer> finitFlux = Flux.range(1, 10)
                .log();

        finitFlux.subscribe(element -> System.out.println("The element is " + element),
                System.err::println,
                () -> System.out.println("Done"),
                Subscription::cancel);
    }

    @Test
    public void backPressure_customized() {

        Flux<Integer> finitFlux = Flux.range(1, 10)
                .log();

        finitFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value received is :" + value);

                if (value == 4) {
                    cancel();
                }
            }
        });
    }
}
