package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entites.Departament;
import model.entites.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                    +"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES (?, ?, ?, ?, ?)"
            , Statement.RETURN_GENERATED_KEYS);
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBitrhDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartament().getId());


            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                    DB.closeResultSet(rs);
                }
            }
            else {
                throw new DbException("Erro Inesperado! ");
            }

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "WHERE Id = ?"
            );
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBitrhDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartament().getId());
            st.setInt(6,obj.getId());

          st.executeUpdate();


        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void delete(Integer id) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM seller " +
                    "WHERE Id = ?");

            st.setInt(1,id);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

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
                Departament departament = instantiateDepartment(res);
                Seller seller = instantiateSeller(res,departament);
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

    private Seller instantiateSeller(ResultSet res, Departament departament) throws SQLException {
        Seller seller = new Seller();
        seller.setId(res.getInt("Id"));
        seller.setName(res.getString("Name"));
        seller.setEmail(res.getString("Email"));
        seller.setBaseSalary(res.getDouble("BaseSalary"));
        seller.setBitrhDate(res.getDate("BirthDate"));
        seller.setDepartament(departament);

        return seller;
    }

    private Departament instantiateDepartment(ResultSet res) throws SQLException {
        Departament departament = new Departament();
        departament.setId(res.getInt("DepartmentId"));
        departament.setName(res.getString("DepName"));

        return departament;

    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()) {

                Departament dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Departament departament) {
        PreparedStatement st = null;
        ResultSet res = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name");

            st.setInt(1, departament.getId());
            res = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            if (res.next()) {

                Departament dep = map.get(res.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(res);
                    map.put(res.getInt("DepartmentId"),dep);
                }

                Seller obj = instantiateSeller(res,dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(res);
        }

    }
}
