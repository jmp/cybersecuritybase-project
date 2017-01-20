package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

}
