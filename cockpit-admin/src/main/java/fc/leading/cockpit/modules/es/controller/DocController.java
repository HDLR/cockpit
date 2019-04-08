package fc.leading.cockpit.modules.es.controller;

import fc.leading.cockpit.modules.es.entity.Doc;
import fc.leading.cockpit.modules.es.repository.DocRepository;
import fc.leading.cockpit.modules.es.entity.Doc;
import fc.leading.cockpit.modules.es.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/es")
public class DocController {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private DocRepository docRepository;

    @RequestMapping("/doc")
    public Doc searchDoc(String id){
        Optional<Doc> docs = docRepository.findById("BUSEXCLIST=02e48176594ed516015972dd30473c2bPRIPID=20190326");
        Doc doc = docs.get();
        return doc;
    }
}
