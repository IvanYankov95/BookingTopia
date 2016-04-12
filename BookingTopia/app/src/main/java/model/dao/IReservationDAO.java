package model.dao;

import org.joda.time.LocalDate;

import java.util.ArrayList;

import model.Reservation;
import model.User;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IReservationDAO {

    public long reserve(Reservation reservation);

    public void removeReservation(Reservation reservation);

    public ArrayList<Reservation> getReservationsByUser(long userId);

    public ArrayList<Reservation> getReservationsByCompany(long companyId);

    public Reservation getReservationsByID(long bookID);

    public void setReservationAsShowed(Reservation reservation);

    public ArrayList<LocalDate> getAllReservedDatesByReservation(long reservationID);
}
