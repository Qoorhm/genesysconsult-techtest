package tech.test.gencons.bean.contact;

import java.util.List;
import java.util.Optional;

import tech.test.gencons.bean.company.Company;

public interface Contact
{
	String getName();

	Optional<String> getTaxCode();

	String getTelephone();

	boolean isFreelance();

	List<Company> getCompanies();

	Long getId();
}
