package com.publicvm.siburarenda.repository;

import com.publicvm.siburarenda.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(String name);

    Optional<Room> findById(Long id);

}
