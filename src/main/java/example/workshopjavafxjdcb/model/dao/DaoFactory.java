package example.workshopjavafxjdcb.model.dao;

import example.workshopjavafxjdcb.db.DB;
import example.workshopjavafxjdcb.model.dao.impl.DepartmentDaoJDBC;
import example.workshopjavafxjdcb.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
