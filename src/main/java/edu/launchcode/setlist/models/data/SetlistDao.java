package edu.launchcode.setlist.models.data;


import edu.launchcode.setlist.models.Setlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SetlistDao extends CrudRepository<Setlist, Integer> {
}
