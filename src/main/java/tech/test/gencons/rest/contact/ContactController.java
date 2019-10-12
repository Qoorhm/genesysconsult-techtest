package tech.test.gencons.rest.contact;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// @PostMapping
	// public ContactBean create(@RequestBody ContactBean contact)
	// {
	// return repository.save(contact);
	// }

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
		res.setTaxNumber(contact.getTelephone());
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

	private static ResponseEntity<ContactBean> response(Optional<Contact> contact)
	{
		return contact.map(c -> createBean(c)).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
}
