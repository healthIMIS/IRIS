package iris.backend_service.alerts.zammad;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
public class ZammadTicketCreator {

	private final RestTemplate rest;
	private final ZammadProperties properties;

	public void createTicket(String ticketTitle, String noteSubject, String message) {

		var ticket = new Ticket(ticketTitle, properties.getGroup(), new Article(noteSubject, message),
				properties.getCustomer());

		var resp = rest.postForEntity(properties.getApiAddress(), ticket, null);

		System.out.println(resp);
	}
}
