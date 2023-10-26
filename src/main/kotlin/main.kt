package io.github.jangalinski.camunda

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main() = runApplication<ExampleApplication>().let { }

@SpringBootApplication
@EnableProcessApplication
@OpenAPIDefinition(
  info = Info(
    title = "Example Application",
    description = "A camunda platform 7 kotlin spring boot showcase.",
    version = "1"
  ),
  externalDocs = ExternalDocumentation(
    description = "camunda cockpit",
    url = "/camunda/app/cockpit/default/#/processes"
  )
)
class ExampleApplication


