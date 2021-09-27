package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

@RunWith(MockitoJUnitRunner.class) // reste edit et create à tester
public class ReservationServiceTest {
	
	@InjectMocks
	private ReservationService reservationService;
	@Mock
	private ReservationDao reservationDao;

	
	@Test(expected=ServiceException.class) 
	public void findbyID_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.findById(1)).thenThrow(new DaoException());
		// Then
		reservationService.findById(1);
	}
	
	@Test
	public void findById_fonctionne() throws DaoException, ServiceException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Reservation reservationTest = new Reservation((long) 0, (long) 2, (long) 2, localDate , localDate);
		// When		
		when(this.reservationDao.findById(0)).thenReturn(reservationTest);
		// Then
		assertEquals(reservationTest, reservationService.findById(0));
	}
	
	@Test(expected=ServiceException.class) 
	public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.findAll()).thenThrow(new DaoException());
		// Then
		reservationService.findAll();
	}
	
	@Test	
	public void findAll_fonctionne() throws DaoException, ServiceException {
		List<Reservation> listeReservation = new ArrayList<>();
		List<Reservation> listeReservationexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Reservation client1 = new Reservation((long) 1, (long) 2, (long) 2, localDate , localDate);
		Reservation client2 = new Reservation((long) 2, (long) 2, (long) 2, localDate , localDate);
		listeReservation.add(client1);
		listeReservation.add(client2);
		listeReservationexpected.add(client1);
		listeReservationexpected.add(client2);
		// When		
		when(this.reservationDao.findAll()).thenReturn(listeReservation);
		// Then
		assertEquals(listeReservationexpected, reservationService.findAll());	 
	}
	
	@Test(expected=ServiceException.class)
	public void delete_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.delete(0)).thenThrow(new DaoException());
		// Then
		reservationService.delete(0);
	}
	
	@Test
	public void delete_fonctionne() throws DaoException, ServiceException {
		// When		
		when(this.reservationDao.delete(1)).thenReturn((long) 1);	
		// Then
		assertEquals(1, reservationService.delete(1));	
	}	
	
	@Test(expected=ServiceException.class)
	public void findResaByClientID_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.findResaByClientId(0)).thenThrow(new DaoException());
		// Then
		reservationService.findResaByClientID(0);
	}
	
	@Test	
	public void findResaByClientID_fonctionne() throws DaoException, ServiceException {		
		List<Reservation> listeReservation = new ArrayList<>();
		List<Reservation> listeReservationexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Reservation client1 = new Reservation((long) 1, (long) 2, (long) 2, localDate , localDate);
		Reservation client2 = new Reservation((long) 2, (long) 2, (long) 2, localDate , localDate);
		listeReservation.add(client1);
		listeReservation.add(client2);
		listeReservationexpected.add(client1);
		listeReservationexpected.add(client2);
		// When		
		when(this.reservationDao.findResaByClientId(2)).thenReturn(listeReservation);
		// Then
		assertEquals(listeReservationexpected, reservationService.findResaByClientID(2));	 
	}	
	
	@Test(expected=ServiceException.class)
	public void findResaByVehicleID_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.findResaByVehicleId(0)).thenThrow(new DaoException());
		// Then
		reservationService.findResaByVehicleID(0);
	}
	
	@Test	
	public void findResaByVehicleID_fonctionne() throws DaoException, ServiceException {		
		List<Reservation> listeReservation = new ArrayList<>();
		List<Reservation> listeReservationexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Reservation client1 = new Reservation((long) 1, (long) 2, (long) 2, localDate , localDate);
		Reservation client2 = new Reservation((long) 2, (long) 2, (long) 2, localDate , localDate);
		listeReservation.add(client1);
		listeReservation.add(client2);
		listeReservationexpected.add(client1);
		listeReservationexpected.add(client2);
		// When		
		when(this.reservationDao.findResaByVehicleId(2)).thenReturn(listeReservation);
		// Then
		assertEquals(listeReservationexpected, reservationService.findResaByVehicleID(2));	 
	}
	
	@Test(expected=ServiceException.class)
	public void NbClientByVehicle_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.NbClientByVehicle(0)).thenThrow(new DaoException());
		// Then
		reservationService.NbClientByVehicle(0);
	}
	
	@Test	
	public void NbClientByVehicle_fonctionne() throws DaoException, ServiceException {		
		// When		
		when(this.reservationDao.NbClientByVehicle(0)).thenReturn(2);
		// Then
		assertEquals(2, reservationService.NbClientByVehicle(0));	 
	}
	
	@Test(expected=ServiceException.class)
	public void NbVehicleByClient_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.NbVehicleByClient(0)).thenThrow(new DaoException());
		// Then
		reservationService.NbVehicleByClient(0);
	}
	
	@Test	
	public void NbVehicleByClient_fonctionne() throws DaoException, ServiceException {		
		// When		
		when(this.reservationDao.NbVehicleByClient(0)).thenReturn(2);
		// Then
		assertEquals(2, reservationService.NbVehicleByClient(0));	 
	}
	
	@Test(expected=ServiceException.class) 
	public void nbreservations_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.count()).thenThrow(new DaoException());
		// Then
		reservationService.nb_reservation();
	}
	
	@Test
	public void nbreservation_fonctionne() throws DaoException, ServiceException {
		List<Reservation> listereservation = new ArrayList<>();
		List<Reservation> listereservationexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Reservation reservation1 = new Reservation((long) 1, (long) 2, (long) 2, localDate , localDate);
		Reservation reservation2 = new Reservation((long) 2, (long) 2, (long) 2, localDate , localDate);
		listereservation.add(reservation1);
		listereservation.add(reservation2);
		listereservationexpected.add(reservation1);
		listereservationexpected.add(reservation2);
		// When		
		when(this.reservationDao.count()).thenReturn(listereservation.size());	
		// Then
		assertEquals(listereservationexpected.size(), reservationService.nb_reservation());	
	}
	
	
	@Test(expected=ServiceException.class)
	public void vehicleIdByClient_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.vehicleIdByClient(0)).thenThrow(new DaoException());
		// Then
		reservationService.vehicleIdByClient(0);
	}
	
	@Test	
	public void vehicleIdByClient_fonctionne() throws DaoException, ServiceException {		
		List<Integer> listeInteger = new ArrayList<>();
		List<Integer> listeIntegerexpected = new ArrayList<>();
		listeInteger.add(1);
		listeInteger.add(3);
		listeIntegerexpected.add(1);
		listeIntegerexpected.add(3);
		// When		
		when(this.reservationDao.vehicleIdByClient(1)).thenReturn(listeInteger);
		// Then
		assertEquals(listeIntegerexpected, reservationService.vehicleIdByClient(1));	 
	}
	
	@Test(expected=ServiceException.class)
	public void clientIdByVehicle_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.reservationDao.clientIdByVehicle(0)).thenThrow(new DaoException());
		// Then
		reservationService.clientIdByVehicle(0);
	}
	
	@Test	
	public void clientIdByVehicle_fonctionne() throws DaoException, ServiceException {		
		List<Integer> listeInteger = new ArrayList<>();
		List<Integer> listeIntegerexpected = new ArrayList<>();
		listeInteger.add(1);
		listeInteger.add(3);
		listeIntegerexpected.add(1);
		listeIntegerexpected.add(3);
		// When		
		when(this.reservationDao.clientIdByVehicle(1)).thenReturn(listeInteger);
		// Then
		assertEquals(listeIntegerexpected, reservationService.clientIdByVehicle(1));	 
	}
	
}
