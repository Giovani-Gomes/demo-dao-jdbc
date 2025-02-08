package Application;

import model.entites.Departament;
import model.entites.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Departament departament = new Departament(1,"Livros");
        System.out.println(departament);

        Seller seller = new Seller(1,"Pedro","Pedro@gmail.com", new Date(),3000.0,departament);
        System.out.println(seller);
    }

}
