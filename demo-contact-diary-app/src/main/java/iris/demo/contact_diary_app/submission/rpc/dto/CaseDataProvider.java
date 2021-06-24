package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CaseDataProvider {
    String firstName;
    String lastName;
    Instant dateOfBirth;
}
