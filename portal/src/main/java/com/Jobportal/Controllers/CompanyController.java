package com.Jobportal.Controllers;

import com.Jobportal.Entities.Company;
import com.Jobportal.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes({"currentCompany"})
public class CompanyController {

    @Autowired
    CompanyService _companyService;

    @RequestMapping("/companyHome")
    public String companyHome(Model model) {
        return "CompanyHome";
    }

}
