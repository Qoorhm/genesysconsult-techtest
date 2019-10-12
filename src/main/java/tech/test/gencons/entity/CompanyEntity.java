package tech.test.gencons.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CompanyEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String taxCode;

	private String mainAddress;

	@ElementCollection
	private List<String> otherAddresses;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<ContactEntity> contacts = new HashSet<>();

	public CompanyEntity()
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

	public Set<ContactEntity> getContacts()
	{
		return contacts;
	}

	public void setContacts(Set<ContactEntity> contacts)
	{
		this.contacts = contacts;
	}

}
