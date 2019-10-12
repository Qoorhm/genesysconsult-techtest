package tech.test.gencons.rest.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
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

import tech.test.gencons.bean.company.Company;
import tech.test.gencons.bean.company.CompanyService;

@RestController
@RequestMapping({ "/companies" })
public class CompanyController
{
	@Autowired
	private CompanyService companyService;

	@Autowired
	CamelContext camelContext;

	CompanyController()
	{
	}

	/*
	Mise à jour d’une entreprise.
	Mise à jour des détails d’une entreprise (adresse).
	Possibilité d’ajouter des adresses à une entreprise et y définir son siège central.
	*/

	@GetMapping
	public List<CompanyBean> findAll()
	{
		return companyService.getCompanies().stream().map(c -> createBean(c)).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<CompanyBean> create(@RequestBody CompanyBean company)
	{
		if (StringUtils.isEmpty(company.getName()) || StringUtils.isEmpty(company.getTaxCode())
				|| StringUtils.isEmpty(company.getMainAddress()))
			return ResponseEntity.badRequest().build();

		Company createdCompany = companyService.createCompany(company.getName(), company.getTaxCode(),
				company.getMainAddress(), company.getOtherAddresses());

		return ResponseEntity.ok(createBean(createdCompany));
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<CompanyBean> update(@PathVariable long id, @RequestBody CompanyBean company)
	{
		return response(
				companyService.updateCompany(id, company.getName(), company.getTaxCode(), company.getMainAddress()));
	}

	private static ResponseEntity<CompanyBean> response(Optional<Company> company)
	{
		return company.map(c -> createBean(c)).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(path = { "/{id}/newAddress" })
	public ResponseEntity<CompanyBean> addAddress(@PathVariable long id, @RequestBody String newAddress)
	{
		if (StringUtils.isEmpty(newAddress))
			return ResponseEntity.badRequest().build();

		return response(companyService.addAddress(id, newAddress));
	}

	private static CompanyBean createBean(Company company)
	{
		CompanyBean res = new CompanyBean(company.getId(), company.getName(), company.getTaxCode(),
				company.getMainAddress(), company.getOtherAddresses());
		return res;
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<CompanyBean> findById(@PathVariable long id)
	{
		return response(companyService.getCompany(id));
	}

	@DeleteMapping(path = { "/{id}" })
	public void deleteById(@PathVariable long id)
	{
		companyService.deleteCompany(id);
	}
}
