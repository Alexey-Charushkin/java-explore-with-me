package ru.practicum.main.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
public class EventRequest {

    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    //    @ManyToOne
//    @JoinColumn(name = "ivent_id")
    @Column(name = "event_id", nullable = false)
    private Integer event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //    @ManyToOne
//    @JoinColumn(name = "requester_id")
    @Column(name = "requester_id", nullable = false)
    private Integer requester;
    @Column(name = "status", nullable = false)
    private EventRequestStatus status;

    public EventRequest(LocalDateTime created, Integer event, Integer id, Integer requester, String status) {
        this.created = created;
        this.event = event;
        this.id = id;
        this.requester = requester;
        this.status = EventRequestStatus.valueOf(status);
    }
}