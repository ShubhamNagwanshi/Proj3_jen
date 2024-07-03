package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ProductNameDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.model.ProductNameInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ProductNameCtl", urlPatterns = { "/ctl/ProductNameCtl" })
public class ProductNameCtl extends BaseCtl {
	protected void preload(HttpServletRequest request) {
		ProductModelInt model = ModelFactory.getInstance().getProductModel();
		try {
			List list = model.list();
			request.setAttribute("productId", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", " name"));

			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", " name must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		} /*
			 * else if (!DataValidator.isName(request.getParameter("price"))) {
			 * request.setAttribute("price", "price must contains number only");
			 * System.out.println(pass); pass = false;
			 * 
			 * }
			 * 
			 * if (DataValidator.isNull(request.getParameter("productId"))) {
			 * request.setAttribute("productId", PropertyReader.getValue("error.require",
			 * "productId")); pass = false; }
			 */

		/*
		 * else if (!DataValidator.isName(request.getParameter("productType"))) {
		 * request.setAttribute("productType",
		 * "productType must contains alphabets only"); System.out.println(pass); pass =
		 * false;
		 * 
		 * }
		 */

		if (!OP_UPDATE.equalsIgnoreCase(request.getParameter("operation"))) {

			/*
			 * if (DataValidator.isNull(request.getParameter("accountNumber"))) {
			 * request.setAttribute("accountNumber",
			 * PropertyReader.getValue("error.require", "accountNumber")); pass = false; }
			 * 
			 * else if (!DataValidator.isAccountNo(request.getParameter("accountNumber"))) {
			 * request.setAttribute("accountNumber",
			 * "Must contain digit Only And Length 6 to 12 "); pass = false;
			 * 
			 * }
			 */
			/*
			 * if (DataValidator.isNull(request.getParameter("pDate"))) {
			 * request.setAttribute("pDate", PropertyReader.getValue("error.require",
			 * "pDate")); pass = false;
			 * 
			 * }
			 */

		}

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ProductNameDTO dto = new ProductNameDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		populateBean(dto, request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ProductNameInt model = ModelFactory.getInstance().getProductNameModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(request.getParameter("id"));
		if (id > 0 || op != null) {
			ProductNameDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("--------dopost run-------");
		// get model
		ProductNameInt model = ModelFactory.getInstance().getProductNameModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("do post" + request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ProductNameDTO dto = (ProductNameDTO) populateDTO(request);
			System.out.println(" in do post method jkjjkjk++++++++" + dto.getId());
			try {
				if (id > 0) {
					model.update(dto);
					System.out.println("do post me update chali");
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ProductNameDTO dto = (ProductNameDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PRODUCTNAME_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRODUCTNAME_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRODUCTNAME_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PRODUCTN_VIEW;
	}

}
