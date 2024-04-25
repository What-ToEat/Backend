package capstone.restaurant.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "TAG_SEQ_GEN" , sequenceName = "TAG_SEQUENCE")
public class Tag {

    @Id
    @GeneratedValue(generator = "TAG_SEQ_GEN")
    private long id;

    private String tagName;

}
