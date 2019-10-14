package tech.test.gencons.bean.contact;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import tech.test.gencons.bean.company.Company;
import tech.test.gencons.bean.company.CompanyFactory;
import tech.test.gencons.bean.company.CompanyService;
import tech.test.gencons.entity.CompanyEntity;
import tech.test.gencons.entity.ContactEntity;
import tech.test.gencons.repo.ContactRepository;

@Component
public class ContactServiceBean implements ContactService
{
	private ContactRepository repository;

	@Autowired
	private CompanyService companyService;

	public ContactServiceBean(ContactRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public Optional<Contact> createContact(String name, Optional<String> taxCode, String firstName, Set<Long> companies)
	{
		ContactEntity contact = new ContactEntity();
		contact.setName(name);
		contact.setFirstName(firstName);
		taxCode.ifPresent(code -> contact.setTaxCode(code));

		for (Long companyId : companies)
		{
			Optional<CompanyEntity> comp = companyService.getEntity(companyId);

			if (comp.isPresent())
			{
				contact.getCompanies().add(comp.get());
				comp.get().getContacts().add(contact);
			}
		}

		if (contact.getCompanies().isEmpty())
		{
			// no good companies -> bad request
			return Optional.empty();
		}

		repository.save(contact);

		return Optional.of(new ContactImpl(contact));
	}

	@Override
	public Optional<Contact> updateContact(long id, String name, Optional<String> taxCode, String firstName,
			Set<Long> companies)
	{
		Optional<ContactEntity> c = get(id);

		if (!c.isPresent())
			return Optional.empty();

		ContactEntity contact = c.get();

		if (!StringUtils.isEmpty(name))
			contact.setName(name);

		taxCode.ifPresent(tc -> contact.setTaxCode(tc));

		if (!StringUtils.isEmpty(firstName))
			contact.setFirstName(firstName);

		if (companies != null)
		{
			for (CompanyEntity company : contact.getCompanies())
			{
				company.getContacts().remove(contact);
			}

			contact.getCompanies().clear();

			for (Long companyId : companies)
			{
				Optional<CompanyEntity> comp = companyService.getEntity(companyId);

				if (comp.isPresent())
				{
					contact.getCompanies().add(comp.get());
					comp.get().getContacts().add(contact);
				}
			}
		}

		repository.save(contact);

		return Optional.of(new ContactImpl(contact));
	}

	@Override
	public boolean deleteContact(long id)
	{
		Optional<ContactEntity> contact = get(id);

		if (contact.isPresent())
		{
			for (CompanyEntity company : contact.get().getCompanies())
			{
				company.getContacts().remove(contact.get());
			}

			repository.delete(contact.get());
			return true;
		}

		return false;
	}

	private Optional<ContactEntity> get(long id)
	{
		try
		{
			ContactEntity entity = repository.getOne(id);

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
	public Optional<Contact> getContact(long id)
	{
		return get(id).map(c -> new ContactImpl(c));
	}

	private static class ContactImpl implements Contact
	{
		private ContactEntity entity;

		ContactImpl(ContactEntity entity)
		{
			this.entity = entity;
		}

		@Override
		public String getName()
		{
			return entity.getName();
		}

		@Override
		public Optional<String> getTaxCode()
		{
			return Optional.ofNullable(entity.getTaxCode());
		}

		@Override
		public String getFirstName()
		{
			return entity.getFirstName();
		}

		@Override
		public List<Company> getCompanies()
		{
			return entity.getCompanies().stream().map(v -> CompanyFactory.createCompany(v))
					.collect(Collectors.toList());
		}

		@Override
		public Long getId()
		{
			return entity.getId();
		}

		@Override
		public boolean isFreelance()
		{
			return entity.getTaxCode() != null;
		}

	}

	@Override
	public List<Contact> getContacts()
	{
		return repository.findAll().stream().map(e -> new ContactImpl(e))
				.sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
	}
}
