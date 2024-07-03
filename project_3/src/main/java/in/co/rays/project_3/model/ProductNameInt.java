package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ProductNameDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ProductNameInt {
	public long add(ProductNameDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(ProductNameDTO dto)throws ApplicationException;
	public void update(ProductNameDTO dto)throws ApplicationException,DuplicateRecordException;
	public ProductNameDTO findByPK(long pk)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(ProductNameDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(ProductNameDTO dto)throws ApplicationException;
	

}
