package co.com.sofka.questions.repositories;


import co.com.sofka.questions.collections.Voto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends ReactiveCrudRepository<Voto, String> {
}
