package io.github.jangalinski.camunda

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main() = runApplication<ExampleApplication>().let {  }

@SpringBootApplication
@EnableProcessApplication
class ExampleApplication {

}
