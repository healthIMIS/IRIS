package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class Contacts {
    List<ContactPerson> contactPersons = null;
    Instant startDate;
    Instant endDate;
}
