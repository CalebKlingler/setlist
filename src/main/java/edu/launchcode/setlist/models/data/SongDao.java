package edu.launchcode.setlist.models.data;

import edu.launchcode.setlist.models.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SongDao extends CrudRepository<Song, Integer> {
}
