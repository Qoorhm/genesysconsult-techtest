package tech.test.gencons.rest.contact;

import java.util.List;

public class ContactBean
{
	private Long id;

	private String name;

	private String telephone;

	private String taxNumber;

	private boolean freelance;

	private List<ContactCompanyBean> companies;

	public ContactBean()
	{

	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getTaxNumber()
	{
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber)
	{
		this.taxNumber = taxNumber;
	}

	public boolean isFreelance()
	{
		return freelance;
	}

	public void setFreelance(boolean freelance)
	{
		this.freelance = freelance;
	}

	public List<ContactCompanyBean> getCompanies()
	{
		return companies;
	}

	public void setCompanies(List<ContactCompanyBean> companies)
	{
		this.companies = companies;
	}

}
