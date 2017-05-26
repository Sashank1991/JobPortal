package com.Jobportal;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.Jobportal.Controllers.BaseController;
import com.Jobportal.Controllers.CompanyController;
import com.Jobportal.Controllers.CompanyRestController;
import com.Jobportal.Controllers.JobPositionRestController;
import com.Jobportal.Controllers.JobSearchRestController;
import com.Jobportal.Controllers.JobSeekerContoller;
import com.Jobportal.Controllers.JobSeekerRestController;
import com.Jobportal.Controllers.RegistrationController;

//import com.Jobportal.Controllers.CompanyRestController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc 
public class PortalApplicationTests
{
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private CompanyRestController companyRestController;
	
	@Autowired
    private JobPositionRestController jobPositionRestController;
	
	@Autowired
    private JobSeekerRestController jobSeekerRestController;
	
	@Autowired
	private BaseController restTemplate;
	
	@Autowired
    private JobSeekerContoller jobSeekerController;
	
	@Autowired
    private CompanyController companyController;
	
	@Autowired
    private JobSearchRestController jobSearchRestController;
	
	@Autowired
    private RegistrationController registrationController;


	
		@Test
		public void contextLoads() {
			
			
		}
	
		@Test
	    public void companyContexLoads() throws Exception {
	        assertThat(companyRestController).isNotNull();
	    }
		
		@Test
	    public void jobPositionContexLoads() throws Exception {
	        assertThat(jobPositionRestController).isNotNull();
	    }
		
		@Test
	    public void jobSeekerContexLoads() throws Exception {
	        assertThat(jobSeekerRestController).isNotNull();
	    }
		
		@Test
	    public void jobSeekercontrollerContexLoads() throws Exception {
	        assertThat(jobSeekerController).isNotNull();
	    }
		
		@Test
	    public void companycontrollerContexLoads() throws Exception {
	        assertThat(companyController).isNotNull();
	    }
		
		@Test
	    public void jobSearchRestcontrollerContexLoads() throws Exception {
	        assertThat(jobSearchRestController).isNotNull();
	    }
		
		
		@Test
	    public void RegistrationcontrollerContexLoads() throws Exception {
	        assertThat(registrationController).isNotNull();
	    }
		
		
		
		
		
		
		
		
		
	   @Test
	   public void testAdd() {
	      String str = "Junit is working fine";
	      assertEquals("Junit is working fine",str);
	   }
	   
	   @Test
	    public void shouldReturnStatusOKForCorrectCompanyId() throws Exception {
	        this.mockMvc.perform(get("/company/12")).andDo(print())
	                .andExpect(status().isOk());
	    }
	   
	   @Test
	    public void shouldReturnStatusOKForCorrectjobPositionId() throws Exception {
	        this.mockMvc.perform(get("/jobPosition/12")).andDo(print())
	                .andExpect(status().isOk());
	    }
	   
	   
	   
	   
	   
	   
	
	
}