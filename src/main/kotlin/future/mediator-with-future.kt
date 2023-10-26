package io.github.jangalinski.camunda.future

import io.github.jangalinski.camunda.BusinessKey
import io.github.jangalinski.camunda.TaskId
import mu.KLogging
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.TaskListener
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/mediator-with-future")
class MediatorWithFutureController(
  private val process: MediatorWithFutureProcess
) {

  @PostMapping
  fun startProcess(@RequestParam businessKey: BusinessKey): ResponseEntity<Unit> {
    val taskId = process.startProcess(businessKey).join()

    return ResponseEntity.created(URI.create("/camunda/app/tasklist/default/#/?task=$taskId")).build()
  }

}

@Component
class MediatorWithFutureProcess(
  private val runtimeService: RuntimeService
) {
  companion object : KLogging() {
    const val NAME = "mediatorWithFutureProcess"
    const val KEY = "mediator-with-future"
    const val MSG_START = "msg_start"
  }

  private val futures = ConcurrentHashMap<BusinessKey, CompletableFuture<TaskId>>()

  val onTaskCreation = TaskListener {
    futures.remove(it.execution.businessKey)?.complete(it.id)
  }

  fun startProcess(businessKey: BusinessKey): CompletableFuture<TaskId> {
    val future = futures.computeIfAbsent(businessKey) { CompletableFuture() }
    runtimeService.startProcessInstanceByMessage(MSG_START, businessKey)

    return future
  }
}
