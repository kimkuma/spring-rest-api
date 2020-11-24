package org.dukdns.kuma04.springrestapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Spring rest api")
                .description("REST API development")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String event1 = "Event";
        String spring = "Spring";

        event.setName(event1);
        event.setDescription(spring);

        assertThat(event.getName()).isEqualTo(event1);
        assertThat(event.getDescription()).isEqualTo(spring);
    }

    @Test
    public void testFree() {
        //Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isTrue();

        //Given
        event = Event.builder()
                .basePrice(10)
                .maxPrice(0)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isFalse();

        //Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isFalse();

        //Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isFalse();
    }

    @Test
    public void testOffline() {
        //Given
        Event event = Event.builder()
                .location("패스트파이브")
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isOffline()).isTrue();

        //Given
        event = Event.builder()
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isOffline()).isFalse();
    }
}