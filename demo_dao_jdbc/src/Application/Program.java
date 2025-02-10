package Application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Departament;
import model.entites.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }

}
