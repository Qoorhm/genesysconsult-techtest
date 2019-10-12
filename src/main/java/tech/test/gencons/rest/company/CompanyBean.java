package tech.test.gencons.rest.company;

import java.util.Collections;
import java.util.List;

public class CompanyBean
{
	private Long id;

	private String name;

	private String taxCode;

	private String mainAddress;

	private List<String> otherAddresses = Collections.emptyList();

	public CompanyBean()
	{

	}

	public CompanyBean(Long id, String name, String taxCode, String address, List<String> otherAddresses)
	{
		this.id = id;
		this.name = name;
		this.taxCode = taxCode;
		mainAddress = address;
		this.otherAddresses = otherAddresses;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTaxCode()
	{
		return taxCode;
	}

	public void setTaxCode(String taxCode)
	{
		this.taxCode = taxCode;
	}

	public String getMainAddress()
	{
		return mainAddress;
	}

	public void setMainAddress(String mainAddress)
	{
		this.mainAddress = mainAddress;
	}

	public List<String> getOtherAddresses()
	{
		return otherAddresses;
	}

	public void setOtherAddresses(List<String> otherAddresses)
	{
		this.otherAddresses = otherAddresses;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
