package example.workshopjavafxjdcb.model.services;

import example.workshopjavafxjdcb.model.dao.DaoFactory;
import example.workshopjavafxjdcb.model.dao.DepartmentDao;
import example.workshopjavafxjdcb.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll(){
        return dao.findAll();
    }

    public void saveorUpdate(Department obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else {
            dao.update(obj);
        }
    }

    public void remove(Department obj){
        dao.deleteById(obj.getId());
    }
}
