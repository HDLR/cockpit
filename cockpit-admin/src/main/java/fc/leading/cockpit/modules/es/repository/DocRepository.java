package fc.leading.cockpit.modules.es.repository;

import fc.leading.cockpit.modules.es.entity.Doc;
import fc.leading.cockpit.modules.es.entity.Doc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocRepository extends ElasticsearchRepository<Doc, String> {

}
