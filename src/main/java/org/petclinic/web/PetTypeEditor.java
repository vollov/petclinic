package org.petclinic.web;

import java.beans.PropertyEditorSupport;

import org.petclinic.domain.Clinic;
import org.petclinic.domain.PetType;

/**
 * @author Mark Fisher
 * @author Juergen Hoeller
 */
public class PetTypeEditor extends PropertyEditorSupport {

	private final Clinic clinic;


	public PetTypeEditor(Clinic clinic) {
		this.clinic = clinic;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (PetType type : this.clinic.getPetTypes()) {
			if (type.getName().equals(text)) {
				setValue(type);
			}
		}
	}

}
