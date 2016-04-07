package model.dao;

import model.Reservation;
import model.User;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IReservationDAO {

    public long reserve(Reservation reservation);

    public void removeReservation(Reservation reservation);

    public Reservation getReservationsByUser(User user);

    public Reservation getReservationsByID(long bookID);
}
