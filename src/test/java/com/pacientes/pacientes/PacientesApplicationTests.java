package com.pacientes.pacientes;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PacientesApplicationTests {

	@Test
	void contextLoads() {
	}

	/*
	 * Verifica que main() invoque SpringApplication.run()
	 * con la clase de la aplicación y los argumentos recibidos,
	 * sin levantar un contexto de Spring real.
	 */
	@Test
	void mainDeberiaInvocarSpringApplicationRun() {

		try (MockedStatic<SpringApplication> springApplicationMock =
				mockStatic(SpringApplication.class)) {

			String[] args = new String[] {};

			PacientesApplication.main(args);

			springApplicationMock.verify(() ->
					SpringApplication.run(PacientesApplication.class, args));
		}
	}

}