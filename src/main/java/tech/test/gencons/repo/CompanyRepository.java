package tech.test.gencons.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.test.gencons.entity.CompanyEntity;
import tech.test.gencons.entity.ContactEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>
{

}
