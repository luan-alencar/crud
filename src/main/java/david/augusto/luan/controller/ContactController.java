package david.augusto.luan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import david.augusto.luan.domain.Contact;
import david.augusto.luan.domain.ContactRepository;

@RestController
@RequestMapping(value = { "/contacts" })
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;

	@GetMapping
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return contactRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.ok().build());
	}

	@PostMapping
	public Contact create(@RequestBody Contact contact) {
		return contactRepository.save(contact);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Contact contact) {
		return contactRepository.findById(id).map(record -> {
			record.setName(contact.getName());
			record.setEmail(contact.getEmail());
			record.setPhone(contact.getPhone());
			Contact updated = contactRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return contactRepository.findById(id).map(record -> {
			contactRepository.deleteById(id);
			return ResponseEntity.ok().body(record);
		}).orElse(ResponseEntity.notFound().build());
	}
}
