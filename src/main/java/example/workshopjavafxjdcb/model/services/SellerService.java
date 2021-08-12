package example.workshopjavafxjdcb.model.services;

import example.workshopjavafxjdcb.model.dao.DaoFactory;
import example.workshopjavafxjdcb.model.dao.SellerDao;
import example.workshopjavafxjdcb.model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll(){
        return dao.findAll();
    }

    public void saveorUpdate(Seller obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}
