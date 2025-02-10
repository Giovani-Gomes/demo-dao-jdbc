package Application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Departament;
import model.entites.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("===== TESTE 1 : Seller findById===== ");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);


        System.out.println("\n===== TESTE 2 : Seller findByDepartment===== ");
        Departament departament = new Departament(4,null);
        List<Seller> list = sellerDao.findByDepartment(departament);
        for (Seller obj: list){
            System.out.println(obj);
        }
    }

}
