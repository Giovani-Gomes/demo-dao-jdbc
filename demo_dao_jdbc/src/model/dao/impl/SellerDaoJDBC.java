package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entites.Departament;
import model.entites.Seller;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet res = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "  // Adicionei um espaço entre "department" e "Name"
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            st.setInt(1, id);
            res = st.executeQuery();

            if (res.next()) {
                Departament departament = new Departament();
                departament.setId(res.getInt("DepartmentId"));
                departament.setName(res.getString("DepName"));
                Seller seller = new Seller();
                seller.setId(res.getInt("Id"));
                seller.setName(res.getString("Name"));
                seller.setEmail(res.getString("Email"));
                seller.setBaseSalary(res.getDouble("BaseSalary"));
                seller.setBitrhDate(res.getDate("BirthDate"));
                seller.setDepartament(departament);

                return seller;
            }
            return null;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(res);
        }
    }
    
    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
