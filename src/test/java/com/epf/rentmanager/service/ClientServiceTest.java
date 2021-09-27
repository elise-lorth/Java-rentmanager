package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

@RunWith(MockitoJUnitRunner.class) // reste edit et create à tester
public class ClientServiceTest {

	@Test
	public void ismail_fonctionne() {
		assertTrue(ClientService.isMail("test@live.fr"));
	}

	@Test
	public void ismail_nefonctionnepas() {
		assertFalse(ClientService.isMail("testlivefr"));
	}

	@InjectMocks
	private ClientService clientService;
	@Mock
	private ClientDao clientDao;

	@Test(expected = ServiceException.class)
	public void isOK_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client client1 = new Client("nom", "prenom", "em@ail", localDate, 1);
		when(ClientService.isOK(client1)).thenThrow(new DaoException());
		// Then
		ClientService.isOK(client1);
	}

	@Test(expected = ServiceException.class)
	public void findbyID_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.findById(1)).thenThrow(new DaoException());
		// Then
		clientService.findById(1);
	}

	@Test
	public void findById_fonctionne() throws DaoException, ServiceException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client clientTest = new Client("nom", "prenom", "em@ail", localDate, 0);
		// When
		when(this.clientDao.findById(0)).thenReturn(clientTest);
		// Then
		assertEquals(clientTest, clientService.findById(0));
	}

	@Test(expected = ServiceException.class)
	public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.findAll()).thenThrow(new DaoException());
		// Then
		clientService.findAll();
	}

	@Test
	public void findAll_fonctionne() throws DaoException, ServiceException {
		List<Client> listeclient = new ArrayList<>();
		List<Client> listeclientexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client client1 = new Client("nom", "prenom", "em@ail", localDate, 1);
		Client client2 = new Client("nom", "prenom", "em@ail", localDate, 2);
		listeclient.add(client1);
		listeclient.add(client2);
		listeclientexpected.add(client1);
		listeclientexpected.add(client2);
		// When
		when(this.clientDao.findAll()).thenReturn(listeclient);
		// Then
		assertEquals(listeclientexpected, clientService.findAll());
	}

	@Test(expected = ServiceException.class)
	public void delete_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.delete(0)).thenThrow(new DaoException());
		// Then
		clientService.delete(0);
	}

	@Test
	public void delete_fonctionne() throws DaoException, ServiceException {
		// When
		when(this.clientDao.delete(1)).thenReturn((long) 1);
		// Then
		assertEquals(1, clientService.delete(1));
	}

	@Test(expected = ServiceException.class)
	public void nbClients_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.count()).thenThrow(new DaoException());
		// Then
		clientService.nb_client();
	}

	@Test
	public void nbClient_fonctionne() throws DaoException, ServiceException {
		List<Client> listeclient = new ArrayList<>();
		List<Client> listeclientexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client client1 = new Client("nom", "prenom", "em@ail", localDate, 1);
		Client client2 = new Client("nom", "prenom", "em@ail", localDate, 2);
		listeclient.add(client1);
		listeclient.add(client2);
		listeclientexpected.add(client1);
		listeclientexpected.add(client2);
		// When
		when(this.clientDao.count()).thenReturn(listeclient.size());
		// Then
		assertEquals(listeclientexpected.size(), clientService.nb_client());
	}

	@Test(expected = DaoException.class)
	public void findbyIDDao_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.findById(1)).thenThrow(new DaoException());
		// Then
		clientDao.findById(1);
	}

	@Test
	public void findByIdDao_fonctionne() throws DaoException, ServiceException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client clientTest = new Client("nom", "prenom", "em@ail", localDate, 0);
		// When
		when(this.clientDao.findById(0)).thenReturn(clientTest);
		// Then
		assertEquals(clientTest, clientDao.findById(0));
	}

	@Test(expected = DaoException.class)
	public void findAllDao_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		// When
		when(this.clientDao.findAll()).thenThrow(new DaoException());
		// Then
		clientDao.findAll();
	}

	@Test
	public void findAllDao_fonctionne() throws DaoException, ServiceException {
		List<Client> listeclient = new ArrayList<>();
		List<Client> listeclientexpected = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Client client1 = new Client("nom", "prenom", "em@ail", localDate, 1);
		Client client2 = new Client("nom", "prenom", "em@ail", localDate, 2);
		listeclient.add(client1);
		listeclient.add(client2);
		listeclientexpected.add(client1);
		listeclientexpected.add(client2);
		// When
		when(this.clientDao.findAll()).thenReturn(listeclient);
		// Then
		assertEquals(listeclientexpected, clientDao.findAll());
	}

//	@Test(expected=ServiceException.class)
//	public void create_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
//		// When
//		when(this.clientService.isOK(null)).thenThrow(new ServiceException());
//		when(this.clientDao.create(null)).thenThrow(new ServiceException());
//		// Then
//		clientService.create(null);
//	}

//	@Test
//	public void create_fonctionne() throws DaoException, ServiceException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
//		Client clientTest = new Client("nom","prenom", "em@ail", localDate, 0);
//		Client clientExpected= new Client("nom","prenom", "em@ail", localDate, 0);
//		// When		
//		when(this.clientDao.create(clientTest)).thenReturn(clientTest.getIdentifiant());	
//		// Then
//		assertEquals(clientTest.getIdentifiant(), clientService.create(clientTest));	
//	}

//	@Test
//	public void isOK_fonctionne() throws DaoException, ServiceException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
//		Client clientTest = new Client("nom","prenom", "test@live.fr", localDate, 1);
//		// When		
//		when(ClientService.isOK(clientTest)).thenReturn(true);
//		// Then
//		assertEquals(true, ClientService.isOK(clientTest));
//	}
}
