package org.petclinic.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.petclinic.domain.Clinic;
import org.petclinic.domain.Owner;
import org.petclinic.domain.Pet;
import org.petclinic.domain.PetType;
import org.petclinic.domain.Vet;
import org.petclinic.domain.Visit;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA implentityManagerentation of the Clinic interface using EntityManager.
 *
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @since 22.4.2006
 */
@Repository
@Transactional
public class PetClinicService implements Clinic {

	@PersistenceContext
	private EntityManager entityManager;


	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Vet> getVets() {
		return this.entityManager.createQuery("SELECT vet FROM Vet vet ORDER BY vet.lastName, vet.firstName").getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<PetType> getPetTypes() {
		return this.entityManager.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Owner> findOwners(String lastName) {
		Query query = this.entityManager.createQuery("SELECT owner FROM Owner owner WHERE owner.lastName LIKE :lastName");
		query.setParameter("lastName", lastName + "%");
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public Owner loadOwner(int id) {
		return this.entityManager.find(Owner.class, id);
	}

	@Transactional(readOnly = true)
	public Pet loadPet(int id) {
		return this.entityManager.find(Pet.class, id);
	}

	public void storeOwner(Owner owner) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Owner merged = this.entityManager.merge(owner);
		this.entityManager.flush();
		owner.setId(merged.getId());
	}

	public void storePet(Pet pet) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Pet merged = this.entityManager.merge(pet);
		this.entityManager.flush();
		pet.setId(merged.getId());
	}

	public void storeVisit(Visit visit) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Visit merged = this.entityManager.merge(visit);
		this.entityManager.flush();
		visit.setId(merged.getId());
	}

	public void deletePet(int id) throws DataAccessException {
		Pet pet = loadPet(id);
		this.entityManager.remove(pet);
	}

}
