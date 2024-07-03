package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ProductModelHibImp implements ProductModelInt {

	public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {

		ProductDTO existDto = null;

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			session.save(dto);

			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Product Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(ProductDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Product Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		/*
		 * Transaction tx = null; ProductDTO exesistDto = findByLogin(dto.getLogin());
		 * 
		 * if (exesistDto != null && exesistDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Login id already exist"); }
		 */
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Product update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public ProductDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		ProductDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (ProductDTO) session.get(ProductDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Product list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<ProductDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getpName() != null && dto.getpName().length() > 0) {
					criteria.add(Restrictions.like("pName", dto.getpName() + "%"));
				}

				if (dto.getPrice() != null && dto.getPrice().length() > 0) {
					criteria.add(Restrictions.like("price", dto.getPrice() + "%"));
				}

				if (dto.getpDate() != null && dto.getpDate().getDate() > 0) {
					criteria.add(Restrictions.eq("pDate", dto.getpDate()));
				}
				
				 if (dto.getProductType() != null && dto.getProductType().length() > 0) {
				  criteria.add(Restrictions.like("productType", dto.getProductType() + "%"));
				  
				 System.out.println(dto.getProductType()); }
				/*
				 * if(dto.getProductId() > 0) { criteria.add(Restrictions.eq("productId",
				 * dto.getProductId())); }
				 */
			}

			System.out.println(" model ki searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<ProductDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Product search");
		} finally {
			session.close();
		}

		return list;
	}

}