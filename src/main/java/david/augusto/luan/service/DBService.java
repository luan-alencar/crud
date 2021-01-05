package david.augusto.luan.service;

import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import david.augusto.luan.domain.Contact;
import david.augusto.luan.domain.ContactRepository;

@Service
public class DBService {

	@Autowired
	private ContactRepository repository;
	
	public void instantiateDatabase() {
		DBService db = new DBService();
		db.init(repository);
	}

    @Bean
    CommandLineRunner init(ContactRepository repository) {
        return args -> {
            repository.deleteAll();
            LongStream.range(1, 11)
                    .mapToObj(i -> {
                        Contact c = new Contact();
                        c.setName("Contact " + i);
                        c.setEmail("contact" + i + "@email.com");
                        c.setPhone("(83) 213-321");
                        return c;
                    })
                    .map(v -> repository.save(v))
                    .forEach(System.out::println);
        };
    }
}
