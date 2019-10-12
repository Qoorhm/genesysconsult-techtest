package tech.test.gencons.bean.contact;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ContactService
{
	Optional<Contact> createContact(String name, Optional<String> taxCode, String telephone, Set<Long> companies);

	Optional<Contact> updateContact(long id, String name, Optional<String> taxCode, String telephone,
			Set<Long> companies);

	boolean deleteContact(long id);

	Optional<Contact> getContact(long id);

	List<Contact> getContacts();
}
