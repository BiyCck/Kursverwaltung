package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @abakhtiari Repository für Raumobjekte
 */

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
