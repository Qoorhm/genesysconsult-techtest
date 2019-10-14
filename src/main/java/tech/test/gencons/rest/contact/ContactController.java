package tech.test.gencons.rest.contact;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.test.gencons.bean.contact.Contact;
import tech.test.gencons.bean.contact.ContactService;

@RestController
@RequestMapping({ "/contacts" })
public class ContactController
{
	@Autowired
	private ContactService contactService;

	ContactController()
	{
	}

	/*
	Création d’un contact.
	Mise à jour d’un contact.
	Suppression d’un contact.
	
	*/

	@PostMapping
	public ResponseEntity<ContactBean> create(@RequestBody ContactBean contact)
	{
		if (StringUtils.isEmpty(contact.getName()) || StringUtils.isEmpty(contact.getFirstName()))
			return ResponseEntity.badRequest().build();

		if (contact.isFreelance() && Strings.isEmpty(contact.getTaxNumber()))
			return ResponseEntity.badRequest().build();

		if ((contact.getCompanies() == null) || contact.getCompanies().isEmpty())
			return ResponseEntity.badRequest().build();

		Set<Long> companyIds = contact.getCompanies().stream().map(e -> e.getId()).filter(id -> id != null)
				.collect(Collectors.toSet());

		Optional<Contact> createdContact = contactService.createContact(contact.getName(),
				Optional.ofNullable(contact.getTaxNumber()), contact.getFirstName(), companyIds);

		if (!createdContact.isPresent()) // creation ko ? bas request
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(createBean(createdContact.get()));
	}

	@GetMapping
	public List<ContactBean> findAll()
	{
		return contactService.getContacts().stream().map(c -> createBean(c)).collect(Collectors.toList());
	}

	private static ContactBean createBean(Contact contact)
	{
		ContactBean res = new ContactBean();
		res.setId(contact.getId());
		res.setName(contact.getName());
		res.setFirstName(contact.getFirstName());
		res.setFreelance(contact.isFreelance());
		contact.getTaxCode().ifPresent(c -> res.setTaxNumber(c));
		res.setCompanies(contact.getCompanies().stream().map(c -> new ContactCompanyBean(c.getId(), c.getName()))
				.collect(Collectors.toList()));

		return res;
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<ContactBean> findById(@PathVariable long id)
	{
		return response(contactService.getContact(id));
	}

	@DeleteMapping(path = { "/{id}" })
	public void deleteById(@PathVariable long id)
	{
		contactService.deleteContact(id);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ContactBean> updateContact(@PathVariable long id, @RequestBody ContactBean contact)
	{
		if (contact.isFreelance() && Strings.isEmpty(contact.getTaxNumber()))
			return ResponseEntity.badRequest().build();

		Set<Long> companyIds = contact.getCompanies().stream().map(e -> e.getId()).filter(cid -> cid != null)
				.collect(Collectors.toSet());

		return response(contactService.updateContact(id, contact.getName(), Optional.ofNullable(contact.getTaxNumber()),
				contact.getFirstName(), companyIds));
	}

	private static ResponseEntity<ContactBean> response(Optional<Contact> contact)
	{
		return contact.map(c -> createBean(c)).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
}
