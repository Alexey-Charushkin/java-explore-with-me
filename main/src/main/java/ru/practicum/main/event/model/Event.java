package ru.practicum.main.event.model;

import lombok.*;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.locations.model.Location;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events", schema = "public")
public class Event {
    @Column(name = "annotation", nullable = false)
    private String annotation;  // Краткое описание

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;  // Количество одобренных заявок на участие в данном событии

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;
    @Column(name = "title", nullable = false)
    private String title;

    @Transient
    private Integer views;



    public Event(String annotation, String description, LocalDateTime parse, Location location,
                 boolean paid, Integer participantLimit, String requestModeration, String title) {
        this.annotation = annotation;
        this.description = description;
        this.eventDate = parse;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        if (requestModeration != null) {
            this.requestModeration = Boolean.parseBoolean(requestModeration);
        }
        this.title = title;
    }


    public boolean isRequestModeration() {
        return requestModeration;
    }

    public boolean isPaid() {
        return paid;
    }
//    public Event(String annotation, CategoryDto category, Integer confirmedRequests, String createdOn,
//                 String description, LocalDateTime eventDate, Integer id, UserShortDto initiator, Location location,
//                 boolean paid, Integer participantLimit, String publishedOn, boolean requestModeration,
//                 Enum state, String title, Integer views) {
//        this.annotation = annotation;
//        this.category = category;
//        this.confirmedRequests = confirmedRequests;
//        this.createdOn = createdOn;
//        this.description = description;
//        this.eventDate = eventDate;
//        this.id = id;
//        this.initiator = initiator;
//        this.location = location;
//        this.paid = paid;
//        this.participantLimit = participantLimit;
//        this.publishedOn = publishedOn;
//        this.requestModeration = requestModeration;
//        this.state = state;
//        this.title = title;
//        this.views = views;
    //   }
}
