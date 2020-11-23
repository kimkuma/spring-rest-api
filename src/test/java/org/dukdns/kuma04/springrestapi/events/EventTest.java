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
}