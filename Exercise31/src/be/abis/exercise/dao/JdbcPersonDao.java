package be.abis.exercise.dao;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Person;

import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private ConnectionFactory connectionFactory;

    public JdbcPersonDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Person> findAllPersons() {
        return null;
    }

    @Override
    public Person findPersonById(int id) {
        return null;
    }

    @Override
    public List<Person> findPersonsByLastName(String firstLetter) {
        return null;
    }

    @Override
    public int countPersonsForCompany(String companyName) {
        return 0;
    }


    @Override
    public void addPerson(Person p) {

    }
}
