package tech.test.gencons.bean.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import tech.test.gencons.entity.CompanyEntity;
import tech.test.gencons.repo.CompanyRepository;

@Component
public class CompanyServiceBean implements CompanyService
{
	private CompanyRepository repository;

	public CompanyServiceBean(CompanyRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public Company createCompany(String name, String taxCode, String address, List<String> otherAddresses)
	{
		CompanyEntity company = new CompanyEntity();
		company.setName(name);
		company.setTaxCode(taxCode);
		company.setMainAddress(address);

		if (otherAddresses != null)
			company.setOtherAddresses(otherAddresses);

		repository.save(company);

		return CompanyFactory.createCompany(company);
	}

	@Override
	public Optional<Company> updateCompany(long id, String name, String taxCode, String address)
	{
		Optional<CompanyEntity> c = get(id);

		if (!c.isPresent())
			return Optional.empty();

		CompanyEntity company = c.get();

		if (!StringUtils.isEmpty(name))
			company.setName(name);

		if (!StringUtils.isEmpty(taxCode))
			company.setTaxCode(taxCode);

		if (!StringUtils.isEmpty(address))
			company.setMainAddress(address);

		repository.save(company);

		return Optional.of(CompanyFactory.createCompany(company));
	}

	@Override
	public boolean deleteCompany(long id)
	{
		Optional<CompanyEntity> company = get(id);

		if (company.isPresent())
		{
			repository.delete(company.get());
			return true;
		}

		return false;
	}

	private Optional<CompanyEntity> get(long id)
	{
		try
		{
			CompanyEntity entity = repository.getOne(id);

			if (entity == null)
				return Optional.empty();

			// ensure entity ok
			entity.getName();
			return Optional.of(entity);
		}
		catch (EntityNotFoundException e)
		{
			return Optional.empty();
		}
	}

	@Override
	public Optional<Company> getCompany(long id)
	{
		return get(id).map(c -> CompanyFactory.createCompany(c));
	}

	@Override
	public List<Company> getCompanies()
	{
		return repository.findAll().stream().map(e -> CompanyFactory.createCompany(e))
				.sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
	}

	@Override
	public Optional<Company> addAddress(long id, String newAddress)
	{
		Optional<CompanyEntity> c = get(id);

		if (!c.isPresent())
			return Optional.empty();

		CompanyEntity company = c.get();
		company.getOtherAddresses().add(newAddress);
		repository.save(company);

		return Optional.of(CompanyFactory.createCompany(company));
	}

	@Override
	public Optional<CompanyEntity> getEntity(long id)
	{
		return get(id);
	}
}
