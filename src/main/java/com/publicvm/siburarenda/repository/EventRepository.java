package com.publicvm.siburarenda.repository;

import com.publicvm.siburarenda.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
}
