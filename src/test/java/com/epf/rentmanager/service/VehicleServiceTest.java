package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;

@RunWith(MockitoJUnitRunner.class) //reste edit et create à tester
public class VehicleServiceTest {

	@InjectMocks
	private VehicleService vehicleService;
	@Mock
	private VehicleDao vehicleDao;

	@Test(expected=ServiceException.class) 
	public void findbyID_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.vehicleDao.findById(1)).thenThrow(new DaoException());
		// Then
		vehicleService.findById(1);
	}
	
	@Test
	public void findById_fonctionne() throws DaoException, ServiceException {
		Vehicle vehiculeTest = new Vehicle("constructeur", "modele",(short) 2,0);
		// When		
		when(this.vehicleDao.findById(0)).thenReturn(vehiculeTest);	
		// Then
		assertEquals(vehiculeTest, vehicleService.findById(0));	 
	}
	
	@Test(expected=ServiceException.class)
	public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.vehicleDao.findAll()).thenThrow(new DaoException());
		// Then
		vehicleService.findAll();
	}
	
	@Test	
	public void findAll_fonctionne() throws DaoException, ServiceException {
		List<Vehicle> listevehicle = new ArrayList<>();
		List<Vehicle> listevehicleexpected = new ArrayList<>();
		Vehicle vehicle1 = new Vehicle("constructeur", "modele",(short) 2,1);
		Vehicle vehicle2 = new Vehicle("constructeur", "modele",(short) 2,2);
		listevehicle.add(vehicle1);
		listevehicle.add(vehicle2);
		listevehicleexpected.add(vehicle1);
		listevehicleexpected.add(vehicle2);
		// When		
		when(this.vehicleDao.findAll()).thenReturn(listevehicle);	
		// Then
		assertEquals(listevehicleexpected, vehicleService.findAll());	
	}
	
	@Test(expected=ServiceException.class) 
	public void delete_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.vehicleDao.delete(0)).thenThrow(new DaoException());
		// Then
		vehicleService.delete(0);
	}
	
	@Test
	public void delete_fonctionne() throws DaoException, ServiceException {		
		// When		
		when(this.vehicleDao.delete(1)).thenReturn((long) 1);
		// Then
		assertEquals(1, vehicleService.delete(1));
	}
	
	
	@Test(expected=ServiceException.class)
	public void nbVoitures_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.vehicleDao.count()).thenThrow(new DaoException());
		// Then
		vehicleService.nb_voiture();
	}
	
	@Test
	public void nbVoitures_fonctionne() throws DaoException, ServiceException {
		List<Vehicle> listevehicle = new ArrayList<>();
		List<Vehicle> listevehicleexpected = new ArrayList<>();
		Vehicle vehicle1 = new Vehicle("constructeur", "modele",(short) 2,1);
		Vehicle vehicle2 = new Vehicle("constructeur", "modele",(short) 2,2);
		listevehicle.add(vehicle1);
		listevehicle.add(vehicle2);
		listevehicleexpected.add(vehicle1);
		listevehicleexpected.add(vehicle2);
		// When		
		when(this.vehicleDao.count()).thenReturn(listevehicle.size());
		// Then
		assertEquals(listevehicleexpected.size(), vehicleService.nb_voiture());	
	}
	
}
