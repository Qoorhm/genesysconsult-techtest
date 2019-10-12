package tech.test.gencons.rest.contact;

public class ContactCompanyBean
{
	private Long id;

	private String name;

	public ContactCompanyBean()
	{

	}

	public ContactCompanyBean(Long id, String name)
	{
		this.id = id;
		this.name = name;
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

}
