package tech.test.gencons.bean.company;

import java.util.List;
import java.util.Optional;

import tech.test.gencons.entity.CompanyEntity;

public interface CompanyService
{
	Company createCompany(String name, String taxCode, String address, List<String> otherAddresses);

	Optional<Company> updateCompany(long id, String name, String taxCode, String address);

	Optional<Company> addAddress(long id, String newAddress);

	boolean deleteCompany(long id);

	Optional<Company> getCompany(long id);

	List<Company> getCompanies();

	Optional<CompanyEntity> getEntity(long id);
}
