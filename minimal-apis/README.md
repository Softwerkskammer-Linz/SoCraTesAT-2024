# Minimal APIs 
### From Controller to Router/Handler in Kotlin with Kotest

* Controller

``` kotlin
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class Controller {
  @GetMapping("/home", produces = [MediaType.TEXT_PLAIN_VALUE])
  fun getHome(): String {
    return "Welcome to the home page!"
  }
}
```

* Controller Test

``` kotlin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest
class ControllerTest(private val webTestClient: WebTestClient) : DescribeSpec({
  describe("API for /api/v1/home") {
    describe("GET") {
      it("returns the home page") {
        val response = webTestClient.get().uri("/api/v1/home").exchange()

        response.expectStatus().isOk
        response.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
        response.expectBody<String>().consumeWith { it.responseBody shouldBe "Welcome to the home page!" }
      }
    }
  }
})
```

* Router

``` kotlin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router(private val handler: Handler) {
    @Bean
    fun routes() = coRouter {
        accept(MediaType.TEXT_PLAIN).nest {
            GET("/api/v2/home", handler::handler)
        }
    }
}
```

* Handler

``` kotlin
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class Handler {
    suspend fun handler(request: ServerRequest): ServerResponse =
        ok().contentType(MediaType.TEXT_PLAIN).bodyValueAndAwait("Welcome to the home page!")
}
```

* Router Test

``` kotlin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest
@ContextConfiguration(classes = [Handler::class])
@Import(Router::class)
class RouterTest(private val webTestClient: WebTestClient) : DescribeSpec({
  describe("API for /api/v2/home") {
    describe("GET") {
      it("returns the home page") {
        val response = webTestClient.get().uri("/api/v2/home").exchange()

        response.expectStatus().isOk
        response.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
        response.expectBody<String>().consumeWith { it.responseBody shouldBe "Welcome to the home page!" }
      }
    }
  }
})
```
