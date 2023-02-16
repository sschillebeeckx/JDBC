package be.abis.exercise.dao;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private ConnectionFactory connectionFactory;

    public JdbcPersonDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Person> findAllPersons() throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abispersons left outer join abiscompanies on pa_cono=cono");
        ResultSet rs = ps.executeQuery();
        List<Person> foundPersons = this.mapPersons(rs);
        conn.close();
        return foundPersons;
    }

    @Override
    public Person findPersonById(int id) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abispersons left outer join abiscompanies on pa_cono=cono where pno=?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        Person foundPerson = this.mapOnePerson(rs);
        connectionFactory.closeConnection(conn);
        return foundPerson;
    }


    @Override
    public List<Person> findPersonsByLastName(String firstLetter) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abispersons left outer join abiscompanies on pa_cono=cono where plname like ?");
        ps.setString(1,firstLetter.toUpperCase()+"%");
        ResultSet rs = ps.executeQuery();
        List<Person> foundPersons = this.mapPersons(rs);
        conn.close();
        return foundPersons;
    }


    @Override
    public int countPersonsForCompany(String companyName) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from abispersons inner join abiscompanies on pa_cono=cono where upper(coname) like ?");
        ps.setString(1,"%" + companyName.toUpperCase()+"%");
        ResultSet rs = ps.executeQuery();
        int nrOfPersons=0;
        if (rs.next())  nrOfPersons=rs.getInt(1);
        conn.close();
        return nrOfPersons;
    }

    @Override
    public void addPerson(Person p) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String insertCompany="insert into abiscompanies(cono,coname,costreet,costrno,cotown,cotownno,cotel,covat) values (?,?,?,?,?,?,?,?)";
        String insertPerson="insert into abispersons(pno,pfname,plname,page,pemail,ppass,plang,pa_cono) values (?,?,?,?,?,?,?,?)";

        PreparedStatement ps =  conn.prepareStatement(insertCompany);
        ps.setInt(1,p.getCompany().getCompanyNumber());
        ps.setString(2,p.getCompany().getName());
        ps.setString(3,p.getCompany().getAddress().getStreet());
        ps.setString(4,p.getCompany().getAddress().getNr());
        ps.setString(5,p.getCompany().getAddress().getZipcode());
        ps.setString(6,p.getCompany().getAddress().getTown());
        ps.setString(7,p.getCompany().getTelephoneNumber());
        ps.setString(8,p.getCompany().getVatNr());

        ps.executeUpdate();

        ps=conn.prepareStatement(insertPerson);
        ps.setInt(1,p.getPersonId());
        ps.setString(2,p.getFirstName());
        ps.setString(3,p.getLastName());
        ps.setInt(4,p.getAge());
        ps.setString(5,p.getEmailAddress());
        ps.setString(6,p.getPassword());
        ps.setString(7,p.getLanguage());
        ps.setInt(8,p.getCompany().getCompanyNumber());

        ps.executeUpdate();

        conn.close();
    }

    private Person mapOnePerson(ResultSet rs) throws SQLException {
        Person p = new Person();
        Company c=null;
        if (rs.next()){
            String companyNumber = rs.getString("pa_cono");
            if (companyNumber!=null){
                Address a = new Address();
                a.setNr(rs.getString("costrno"));
                a.setStreet(rs.getString("costreet"));
                a.setZipcode(rs.getString("cotownno"));
                a.setTown(rs.getString("cotown").trim());
                c = new Company();
                c.setName(rs.getString("coname").trim());
                c.setTelephoneNumber(rs.getString("cotel"));
                c.setVatNr(rs.getString("covat"));
                c.setAddress(a);
            }
            p.setPersonId(rs.getInt("pno"));
            p.setFirstName(rs.getString("pfname"));
            p.setLastName(rs.getString("plname").trim());

            String age = rs.getString("page");
            if(age!=null){
                p.setAge(Integer.parseInt(age));
            } else {
                p.setAge(0);
            }

            p.setEmailAddress(rs.getString("pemail"));
            p.setPassword(rs.getString("ppass"));
            p.setLanguage(rs.getString("plang"));
            p.setCompany(c);
        }
        return p;
    }

    private List<Person> mapPersons(ResultSet rs) throws SQLException {
        List<Person> persons = new ArrayList<>();
        while(rs.next()){
            Person p = new Person();
            Company c=null;
            String companyNumber = rs.getString("pa_cono");
            if (companyNumber!=null){
                Address a = new Address();
                a.setNr(rs.getString("costrno"));
                a.setStreet(rs.getString("costreet"));
                a.setZipcode(rs.getString("cotownno"));
                a.setTown(rs.getString("cotown").trim());
                c = new Company();
                c.setName(rs.getString("coname").trim());
                c.setTelephoneNumber(rs.getString("cotel"));
                c.setVatNr(rs.getString("covat"));
                c.setAddress(a);
            }
            p.setPersonId(rs.getInt("pno"));
            p.setFirstName(rs.getString("pfname"));
            p.setLastName(rs.getString("plname").trim());

            String age = rs.getString("page");
            if(age!=null){
                p.setAge(Integer.parseInt(age));
            } else {
                p.setAge(0);
            }

            p.setEmailAddress(rs.getString("pemail"));
            p.setPassword(rs.getString("ppass"));
            p.setLanguage(rs.getString("plang"));
            p.setCompany(c);
            persons.add(p);
        }
        return persons;
    }
}
