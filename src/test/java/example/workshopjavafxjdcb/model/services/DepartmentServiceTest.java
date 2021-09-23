package example.workshopjavafxjdcb.model.services;

import example.workshopjavafxjdcb.model.dao.DaoFactory;
import example.workshopjavafxjdcb.model.dao.DepartmentDao;
import example.workshopjavafxjdcb.model.entities.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DepartmentServiceTest {

    private DepartmentService ds = new DepartmentService();
    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    @Test
    void testaRetornoListaDepartamentoso(){
        List<Department> vendedores = ds.findAll();
        Assertions.assertNotNull(vendedores);
        Assertions.assertEquals(13,vendedores.size());
    }

    @Test
    void insertDepartment(){
        Department department = new Department(null, "Teste");
        ds.saveorUpdate(department);

        Department departmentBanco = dao.findById(department.getId());
        Assertions.assertEquals(department.getName(), departmentBanco.getName());
        ds.remove(department);
    }

    @Test
    void testUpdate(){
        Department department = new Department(null, "TesteInserção");
        ds.saveorUpdate(department);

        department.setName("Update");
        ds.saveorUpdate(department);

        Department departamentoBanco = dao.findById(department.getId());
        Assertions.assertEquals("Update", departamentoBanco.getName());
        ds.remove(department);
    }

    @Test
    void testeDelete(){
        Department department = new Department(null, "TesteDelete");
        ds.saveorUpdate(department);
        ds.remove(department);
        Assertions.assertEquals(null, dao.findById(department.getId()));
    }


}