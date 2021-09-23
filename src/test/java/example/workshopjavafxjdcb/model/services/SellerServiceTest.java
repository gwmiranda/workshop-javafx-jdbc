package example.workshopjavafxjdcb.model.services;

import example.workshopjavafxjdcb.model.dao.DaoFactory;
import example.workshopjavafxjdcb.model.dao.DepartmentDao;
import example.workshopjavafxjdcb.model.dao.SellerDao;
import example.workshopjavafxjdcb.model.entities.Department;
import example.workshopjavafxjdcb.model.entities.Seller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class SellerServiceTest {

    private SellerDao daoSeller = DaoFactory.createSellerDao();
    private DepartmentDao daoDepartment = DaoFactory.createDepartmentDao();
    private SellerService sellerService = new SellerService();
    private DepartmentService departmentService = new DepartmentService();


    @Test
    void testaRetornoListaVendedoresCerto(){
        List<Seller> vendedores = sellerService.findAll();
        if (vendedores.size() > 1) {
            Assertions.assertNotNull(vendedores);
        } else {
            Assertions.assertNull(vendedores);
        }
        Assertions.assertEquals(12,vendedores.size());
    }

    @Test
    void testaSalvarVendedor(){
        Department department = daoDepartment.findById(2);
        Seller seller = new Seller();

        seller.setId(null);
        seller.setName("Gui_Insert");
        seller.setEmail("guilherme@gmail.com");
        seller.setBirthDate(new java.sql.Date(Date.parse("22/09/2021")));
        seller.setDepartment(department);
        seller.setBaseSalary(2000.00);

        sellerService.saveorUpdate(seller);

        Seller pessoaSalva = daoSeller.findById(seller.getId());
        Assertions.assertEquals("Gui_Insert", pessoaSalva.getName());
        sellerService.remove(seller);
    }

    @Test
    void testaEditarVendedor(){
        Department department = daoDepartment.findById(2);
        Seller seller = new Seller();

        seller.setId(null);
        seller.setName("Gui_Insert");
        seller.setEmail("guilherme@gmail.com");
        seller.setBirthDate(new java.sql.Date(Date.parse("22/09/2021")));
        seller.setDepartment(department);
        seller.setBaseSalary(2000.00);

        sellerService.saveorUpdate(seller);

        seller.setName("Gui_Atualizado");
        sellerService.saveorUpdate(seller);

        Seller sellerBanco = daoSeller.findById(seller.getId());
        Assertions.assertEquals("Gui_Atualizado", sellerBanco.getName());
        sellerService.remove(seller);
    }

    @Test
    void testaRemoverVendedor(){
        Department department = daoDepartment.findById(2);
        Seller seller = new Seller();

        seller.setId(null);
        seller.setName("Gui_Insert");
        seller.setEmail("guilherme@gmail.com");
        seller.setBirthDate(new java.sql.Date(Date.parse("22/09/2021")));
        seller.setDepartment(department);
        seller.setBaseSalary(2000.00);

        sellerService.saveorUpdate(seller);
        sellerService.remove(seller);

        Assertions.assertEquals(null, daoSeller.findById(seller.getId()));
    }
}