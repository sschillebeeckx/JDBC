package be.abis.exercise.dao;

import be.abis.exercise.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao {

    public List<Person> findAllPersons() throws SQLException;
    public Person findPersonById(int id) throws SQLException;
    public List<Person> findPersonsByLastName(String firstLetter) throws SQLException;
    public int countPersonsForCompany(String companyName) throws SQLException;
    public void addPerson(Person p);

}
