package tech.test.gencons.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.test.gencons.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long>
{

}
