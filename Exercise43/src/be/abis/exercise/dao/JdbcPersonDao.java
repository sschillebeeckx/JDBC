package be.abis.exercise.dao;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

import java.sql.*;
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
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Person foundPerson = this.mapOnePerson(rs);
        connectionFactory.closeConnection(conn);
        return foundPerson;
    }


    @Override
    public List<Person> findPersonsByLastName(String firstLetter) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String sql = "{call get_persons_lastname(?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setString("firstLetter", firstLetter);
        cs.registerOutParameter("person_list", Types.REF_CURSOR);
        cs.executeUpdate();
        ResultSet rs = (ResultSet) cs.getObject("person_list");
        List<Person> foundPersons = this.mapPersons(rs);
        conn.close();
        return foundPersons;
    }


    @Override
    public int countPersonsForCompany(String companyName) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String sql = "{call nrof_persons_company(?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setString(1, "%" + companyName.toUpperCase() + "%");
        cs.registerOutParameter(2, Types.INTEGER);
        cs.execute();
        int nrOfPersons = cs.getInt(2);
        conn.close();
        return nrOfPersons;
    }

    @Override
    public void addPerson(Person p) throws SQLException {
        Connection conn=null;
        Savepoint sp1=null;
        try {
            conn = connectionFactory.createConnection();

            conn.setAutoCommit(false);

            String findCompany = "select * from abiscompanies where rtrim(coname)=?";
            String findMaxCono = "select max(cono) from abiscompanies";
            String findMaxPno = "select max(pno) from abispersons";
            String insertCompany = "insert into abiscompanies(cono,coname,costreet,costrno,cotown,cotownno,cotel,covat) values (?,?,?,?,?,?,?,?)";
            String insertPerson = "insert into abispersons(pno,pfname,plname,page,pemail,ppass,plang,pa_cono) values (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = null;

            int newCono = 0;

            if (p.getCompany() != null) {

                ps = conn.prepareStatement(findCompany);
                ps.setString(1, p.getCompany().getName());
                ResultSet rs = ps.executeQuery();
                Company foundComp = this.mapOneCompany(rs);

                if (foundComp == null) {

                    ps = conn.prepareStatement(findMaxCono);
                    ResultSet rs1 = ps.executeQuery();

                    if (rs1.next()) {
                        newCono = rs1.getInt(1) + 1;
                    }

                    ps = conn.prepareStatement(insertCompany);
                    ps.setInt(1, newCono);
                    ps.setString(2, p.getCompany().getName());
                    ps.setString(3, p.getCompany().getAddress().getStreet());
                    ps.setString(4, p.getCompany().getAddress().getNr());
                    ps.setString(5, p.getCompany().getAddress().getZipcode());
                    ps.setString(6, p.getCompany().getAddress().getTown());
                    ps.setString(7, p.getCompany().getTelephoneNumber());
                    ps.setString(8, p.getCompany().getVatNr());

                    ps.executeUpdate();

                    System.out.println("using new compnumber: "+ newCono);

                } else {
                    System.out.println("found company " + foundComp.getCompanyNumber() + ":" + foundComp.getName());
                    newCono = foundComp.getCompanyNumber();
                    System.out.println("using existing compnumber: "+ newCono);
                }
            }

            sp1 = conn.setSavepoint("compsp");

            ps = conn.prepareStatement(findMaxPno);
            ResultSet rs2 = ps.executeQuery();
            int newPno = 0;
            if (rs2.next()) {
                newPno = rs2.getInt(1) + 1;
            }
            System.out.println("new pno: " + newPno);
            ps = conn.prepareStatement(insertPerson);
            ps.setInt(1, newPno);
            ps.setString(2, p.getFirstName());
            if (p.getLastName()!=null && !p.getLastName().equals("")) {
                ps.setString(3, p.getLastName());
            } else {
                ps.setNull(3,Types.CHAR);
            }
            ps.setInt(4, p.getAge());
            ps.setString(5, p.getEmailAddress());
            ps.setString(6, p.getPassword());
            ps.setString(7, p.getLanguage());

            if (p.getCompany() != null) {
                ps.setInt(8, newCono);
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            ps.executeUpdate();

            conn.commit();
        } catch(SQLException e){
            conn.rollback(sp1);
        } finally {
            conn.close();
        }
    }

    private Person mapOnePerson(ResultSet rs) throws SQLException {
        Person p = new Person();
        Company c = null;
        if (rs.next()) {
            String companyNumber = rs.getString("pa_cono");
            if (companyNumber != null) {
                Address a = new Address();
                a.setNr(rs.getString("costrno"));
                a.setStreet(rs.getString("costreet"));
                a.setZipcode(rs.getString("cotownno"));
                a.setTown(rs.getString("cotown").trim());
                c = new Company();
                c.setCompanyNumber(rs.getInt("cono"));
                c.setName(rs.getString("coname").trim());
                c.setTelephoneNumber(rs.getString("cotel"));
                c.setVatNr(rs.getString("covat"));
                c.setAddress(a);
            }
            p.setPersonId(rs.getInt("pno"));
            p.setFirstName(rs.getString("pfname"));
            p.setLastName(rs.getString("plname").trim());

            String age = rs.getString("page");
            if (age != null) {
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
        while (rs.next()) {
            Person p = new Person();
            Company c = null;
            String companyNumber = rs.getString("pa_cono");
            if (companyNumber != null) {
                Address a = new Address();
                a.setNr(rs.getString("costrno"));
                a.setStreet(rs.getString("costreet"));
                a.setZipcode(rs.getString("cotownno"));
                a.setTown(rs.getString("cotown").trim());
                c = new Company();
                c.setCompanyNumber(rs.getInt("cono"));
                c.setName(rs.getString("coname").trim());
                c.setTelephoneNumber(rs.getString("cotel"));
                c.setVatNr(rs.getString("covat"));
                c.setAddress(a);
            }
            p.setPersonId(rs.getInt("pno"));
            p.setFirstName(rs.getString("pfname"));
            p.setLastName(rs.getString("plname").trim());

            String age = rs.getString("page");
            if (age != null) {
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

    private Company mapOneCompany(ResultSet rs) throws SQLException {
        Company c = null;
        if (rs.next()) {
            Address a = new Address();
            a.setNr(rs.getString("costrno"));
            a.setStreet(rs.getString("costreet"));
            a.setZipcode(rs.getString("cotownno"));
            a.setTown(rs.getString("cotown").trim());
            c = new Company();
            c.setCompanyNumber(rs.getInt("cono"));
            c.setName(rs.getString("coname").trim());
            c.setTelephoneNumber(rs.getString("cotel"));
            c.setVatNr(rs.getString("covat"));
            c.setAddress(a);
        }
        return c;
    }
}
