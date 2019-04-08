package fc.leading.cockpit.modules.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "check_info",type = "info")
public class Doc {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String database_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatabase_name() {
        return database_name;
    }

    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }
}
